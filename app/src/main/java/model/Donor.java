package model;

import java.io.Serializable;

public class Donor extends User implements Serializable {

	int donation_count;
	int visibility;
	int rank_id;
	String last_donation_date;

	public Donor() {
	}

	public Donor(int userId, int donation_count, int visibility, int rank_id, String last_donation_date) {
		super(userId);
		this.donation_count = donation_count;
		this.visibility = visibility;
		this.rank_id = rank_id;
		this.last_donation_date = last_donation_date;
	}

	public Donor(int userId, String firstName, String lastName, String phoneNumber, String blood_group, String gender, String photoUrl, String district, String police_station, String location, int status, String email, String password, int donation_count, int visibility, int rank_id, String last_donation_date) {
		super(userId, firstName, lastName, phoneNumber, blood_group, gender, photoUrl, district, police_station, location, status, email, password);
		this.donation_count = donation_count;
		this.visibility = visibility;
		this.rank_id = rank_id;
		this.last_donation_date = last_donation_date;
	}


	public int getDonation_count() {
		return donation_count;
	}

	public void setDonation_count(int donation_count) {
		this.donation_count = donation_count;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getRank_id() {
		return rank_id;
	}

	public void setRank_id(int rank_id) {
		this.rank_id = rank_id;
	}

	public String getLast_donation_date() {
		return last_donation_date;
	}

	public void setLast_donation_date(String last_donation_date) {
		this.last_donation_date = last_donation_date;
	}

}
