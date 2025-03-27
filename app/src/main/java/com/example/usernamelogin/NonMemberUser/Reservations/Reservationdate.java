package com.example.usernamelogin.NonMemberUser.Reservations;


    public class Reservationdate {
        private String date;
        private String user;
        private String time;
        private String res_id;
        private String gym;
        private String gymnum;
        String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public Reservationdate() {
            // Default constructor required for Firebase
        }

        public Reservationdate(String userid, String gymnum, String res_id, String gym, String time, String user, String date) {
            this.userid = userid;
            this.gymnum = gymnum;
            this.res_id = res_id;
            this.gym = gym;
            this.time = time;
            this.user = user;
            this.date = date;
        }
        // Getters and setters for Reservation properties

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
        public String getRes_id() { return res_id;}
        public void setRes_id(String res_id) {
            this.res_id = res_id;
        }

        public String getGym() {
            return gym;
        }

        public void setGym(String gym) {
            this.gym = gym;
        }

        public String getGymnum() {
            return gymnum;
        }

        public void setGymnum(String gymnum) {
            this.gymnum = gymnum;
        }
    }


