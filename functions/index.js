const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.checkMembershipExpirations = functions.pubsub.schedule("every 24 hours")
    .onRun(async (context) => {
      const nonMembersRef = admin.database().ref("Users/Non-members");

      try {
        const snapshot = await nonMembersRef.once("value");
        snapshot.forEach((nonMemberSnapshot) => {
          const userId = nonMemberSnapshot.key;
          const membershipStatus = nonMemberSnapshot
              .child("membership_status").val();
          const membership = nonMemberSnapshot.child("membership").val();

          if (membershipStatus === 0 && membership) {
            const {expirationDate} = membership;

            if (isExpired(expirationDate)) {
              nonMembersRef.child(userId)
                  .child("membership").remove((error) => {
                    if (error) {
                      console.error("Error removing membership node:", error);
                    } else {
                      nonMembersRef.child(userId)
                          .child("membership_status").set(1)
                          .catch((error) => {
                            console
                                .error("Error updating membership:", error);
                          });
                    }
                  });
            }
          }
        });
      } catch (error) {
        console.error("Error checking membership expirations:", error);
      }

      return null; // Indicate that the function has finished processing
    });

/**
 * Checks if the membership expiration date has passed.
 * @param {string} expirationDate - The expiration date of the membership.
 * @return {boolean} - True if the membership is expired, otherwise false.
 */
function isExpired(expirationDate) {
  const currentDateTime = new Date();
  const expirationDateTime = new Date(expirationDate);
  return currentDateTime > expirationDateTime;
}
