// Import the necessary Firebase SDKs
const functions = require("firebase-functions");
const admin = require("firebase-admin");

// Initialize the Firebase Admin SDK.
// IMPORTANT: Since your RTDB is in asia-southeast1 and is NOT the default
// database for the *project*, you MUST specify the database URL here.
admin.initializeApp({
  // This databaseURL is based on the fact you provided for gym-bookinga01-default-rtdb
  databaseURL: "https://gym-bookinga01-default-rtdb.asia-southeast1.firebasedatabase.app"
});

// Get a reference to the Realtime Database
const db = admin.database();

// --- Helper function to parse "Month Day Year" string ---
// Parses dates like "3 22 25". Assumes the year is 2-digit and represents 20YY (e.g., "25" means 2025)
function parseMonthDayYear(dateString) {
  if (!dateString || typeof dateString !== 'string') {
    return null; // Return null for invalid input
  }
  const parts = dateString.split(' '); // Split by space
  if (parts.length !== 3) {
    console.warn(`Invalid date string format: "${dateString}". Expected "Month Day Year".`);
    return null; // Expecting 3 parts: Month Day Year
  }

  const month = parseInt(parts[0], 10); // Month is the first part (1-12)
  const day = parseInt(parts[1], 10);   // Day is the second part (1-31)
  let year = parseInt(parts[2], 10);     // Year is the third part (e.g., 25)

  // Basic validation for numbers and ranges
  if (isNaN(month) || isNaN(day) || isNaN(year) || month < 1 || month > 12 || day < 1 || day > 31) {
      console.warn(`Invalid date parts in string: "${dateString}"`);
      return null;
  }

  // Assume 2-digit year is 20YY
  // This conversion is crucial for correctly creating the Date object.
  if (year >= 0 && year < 100) {
      year += 2000;
  } else if (year < 1900 || year > 2100) { // Basic sanity check for potentially 4-digit years
       console.warn(`Unusual year format or value: ${parts[2]} in date string "${dateString}"`);
       // You might want to adjust this handling based on expected year ranges.
  }

  // Create a Date object using UTC time to avoid local timezone effects during creation
  // We will compare using timestamps later, which is timezone safe.
  // Month is 0-indexed in JavaScript Date objects, hence `month - 1`.
  const date = new Date(Date.UTC(year, month - 1, day));

   // Check if the created date is valid (e.g., handles invalid dates like Feb 30)
   // Check year, month, and day components of the created date object against the original parts.
   // Use getUTCFullYear, getUTCMonth, getUTCDate because we created a UTC date object.
   if (date.getUTCFullYear() !== year || date.getUTCMonth() !== month - 1 || date.getUTCDate() !== day) {
        console.warn(`Date object creation failed or resulted in different date for string: "${dateString}". Resulting date components (UTC): Year=${date.getUTCFullYear()}, Month=${date.getUTCMonth() + 1}, Day=${date.getUTCDate()}`);
        return null;
   }


  return date; // Return the JavaScript Date object
}


// --- Define the Scheduled Function ---

// This function is scheduled to run once every day at midnight (00:00).
// The schedule format '0 0 * * *' is a standard cron expression.
// You could also use 'every 24 hours' or similar simpler syntax.
exports.checkAndModifyExpiredMemberships = functions.pubsub.schedule('0 0 * * *')
  // IMPORTANT: Set a timezone appropriate for your application.
  // This timezone determines when the '0 0 * * *' schedule actually triggers.
  // 'Asia/Singapore' was used as an example based on the database location fact,
  // but pick the one relevant to your needs or use 'UTC'.
  .timeZone('Asia/Manila')// <-- CHANGE THIS TO YOUR DESIRED TIMEZONE
  .onRun(async (context) => {

    console.log('Running daily check for expired memberships and applying modifications...');

    // --- Configuration: Path to the "Non-members" node ---
    // This is the path that contains the list of user IDs (the items we will iterate through).
    const nonMembersPath = 'Users/Non-members'; // Correct path based on your structure

    // The name of the property within the 'membership' object that holds the expiry date string.
    const expiryPropertyName = 'expiration_date'; // Correct property name

    // Get the current date as a JavaScript Date object for comparison.
    // We compare timestamps, which is timezone safe.
    // Set the time to the beginning of the day (UTC) for consistent "end of day" expiry checks.
    const now = new Date();
    now.setUTCHours(0, 0, 0, 0); // Set time to midnight UTC (start of the day) for comparison

    try {
      // Get a snapshot of the data at the "Non-members" path
      const snapshot = await db.ref(nonMembersPath).once('value');

      if (!snapshot.exists()) {
        console.log(`No data found at path: ${nonMembersPath}`);
        return null; // Nothing to check
      }

      const totalUsers = snapshot.numChildren();
      console.log(`Checking ${totalUsers} potential members...`);

      const updatePromises = []; // Use updatePromises as we're using the update method
      let expiredCount = 0;

      // Iterate through each user ID under "Non-members"
      snapshot.forEach((userSnapshot) => {
        const userId = userSnapshot.key; // The unique ID like "-NuPo0opVlQCZ1biRH3i"
        const userData = userSnapshot.val(); // The data for this specific user ID

        // Navigate to the membership object and get the expiration date string
        const membershipData = userData && userData.membership;
        const expiryDateString = membershipData && membershipData[expiryPropertyName];

        // Check if the expiration date string exists
        if (expiryDateString) {
          // Parse the date string using our helper function
          const expiryDate = parseMonthDayYear(expiryDateString);

          if (expiryDate) {
              // Compare the parsed expiry date timestamp to the current date timestamp (start of the day)
              // If the expiry date is BEFORE today (now), it's considered expired.
              if (expiryDate.getTime() < now.getTime()) {
                  console.log(`Membership for user ${userId} (Expiry: ${expiryDateString}, Parsed UTC: ${expiryDate.toISOString()}) has expired. Applying modifications...`);
                  expiredCount++;

                  // --- Define the updates/deletions for this specific user node ---
                  // Keys are paths relative to the user's node reference (userSnapshot.ref)
                  // Value null means delete the node at that path
                  // Paths confirmed based on your shared structure snippets
                  const updates = {
                      'membership': null,             // Delete the membership node
                      'GymName': null,                // Delete the GymName node
                      'positionstored': null,         // Delete the positionstored node (including its children)
                      'Coach_Reservation': null,      // Delete the Coach_Reservation node
                      'membership_status': 0          // Set membership_status to 0
                      // If there are other nodes you want to delete or modify for expired users, add them here.
                      // e.g., 'email': null, // to delete the email node
                      //       'username': 'expired' // to change the username
                  };

                  // Get a reference to the specific user's node
                  const userRef = userSnapshot.ref;
                  updatePromises.push(userRef.update(updates) // Use the update method for multiple changes
                      .then(() => {
                        console.log(`Successfully applied modifications for user ${userId}.`);
                      })
                      .catch((error) => {
                        console.error(`Error applying modifications for user ${userId} at path ${userRef.toString()}:`, error);
                      })
                  );
              } else {
                  // Optional: Log users whose membership is not expired
                  // console.log(`Membership for user ${userId} (Expiry: ${expiryDateString}, Parsed UTC: ${expiryDate.toISOString()}) is not expired.`);
              }
          } else {
              // Handle cases where the date string format was invalid or parsing failed
              console.warn(`Skipping modifications for user ${userId} due to invalid or unparseable expiration date string: "${expiryDateString}".`);
          }
        } else {
           // Optional: Log users missing the membership or expiration_date property
           // console.log(`User ${userId} is missing membership or ${expiryPropertyName} property.`);
        }
      });

      // Wait for all update/deletion promises to complete
      // This ensures the function doesn't exit until all database writes are attempted.
      await Promise.all(updatePromises);

      console.log(`Finished checking and modifying expired memberships.`);
      console.log(`Total users checked: ${totalUsers}. Memberships expired and modified: ${expiredCount}.`);


      return null; // Indicate successful execution of the function

    } catch (error) {
      console.error("An error occurred during the daily expiration check process:", error);
      // Depending on how critical it is, you might want to re-throw the error
      // so Cloud Functions reports it as a failure and potentially retries.
      // throw error;
      return null; // Returning null indicates the function completed its execution cycle.
    }
});

// You can add other Cloud Functions here if needed for your exp_check codebase.
// For example:
// exports.anotherFunction = functions.https.onRequest((request, response) => { /* ... */ });
