package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.BloodBank;
import model.Donor;
import model.User;

import static android.support.constraint.Constraints.TAG;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION=8;
	private static final String DATABASE_NAME="BloodBook";

	//Table names
	private static final String TABLE_USER="User";
	private static final String TABLE_DONOR="Donor";
	private static final String TABLE_DONATION_REQUEST="DonationRequest";
	private static final String TABLE_MED_DOC="MedDocument";
    private static final String TABLE_BloodBank="BloodBank";
	private static final String TABLE_City="City";


	private static final String KEY_ID="id";
	private static final String KEY_STATUS="status";

	//User
	private static final String KEY_EMAIL="email";
	private static final String KEY_FNAME="first_name";
	private static final String KEY_LNAME="last_name";
	private static final String KEY_BLOOD_GROUP="blood_group";
	private static final String KEY_GENDER="gender";
    private static final String KEY_CONTACTNO="phone_no";
	private static final String KEY_DISTRICT="district";
	private static final String KEY_POLICE_STATION="police_station";
	private static final String KEY_LOCATION="location";
	private static final String KEY_PASSWORD="password";
	private static final String KEY_PHOTO_URL="photo_url";

	String CREATE_USER_TABLE="CREATE TABLE "+ TABLE_USER
			+"("
			+ KEY_ID +" INTEGER PRIMARY KEY, "
			+ KEY_EMAIL +" TEXT NOT NULL UNIQUE, "
			+ KEY_PASSWORD +" TEXT ,"
			+ KEY_FNAME +" TEXT, "
			+ KEY_LNAME +" TEXT, "
			+ KEY_BLOOD_GROUP +" TEXT, "
			+ KEY_GENDER +" TEXT, "
            + KEY_CONTACTNO +" TEXT, "
			+ KEY_DISTRICT +" TEXT, "
			+ KEY_POLICE_STATION +" TEXT, "
			+ KEY_LOCATION +" TEXT, "
			+ KEY_STATUS +" INTEGER, "
			+ KEY_PHOTO_URL +" TEXT "
			+")";

	//Donor
	private static final String KEY_DONOR_STATUS="status";
	private static final String KEY_DONATION_COUNT="donation_count";
	private static final String KEY_VISIBILITY="visibility";
	private static final String KEY_LAST_DONATION_DATE="last_donation_date";
	private static final String KEY_RANK_ID="rank_id";

	String CREATE_DONOR_TABLE="CREATE TABLE "+ TABLE_DONOR
			+"("
			+ KEY_ID +" INTEGER PRIMARY KEY  , "
			+ KEY_DONATION_COUNT +" INTEGER, "
			+ KEY_VISIBILITY +" INTEGER, "
			+ KEY_LAST_DONATION_DATE +" DATETIME, "
			+ KEY_RANK_ID +" INTEGER, "
			+" FOREIGN KEY ("+KEY_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+") ON DELETE CASCADE"
			+")";

	//Request
	private static final String KEY_SENDER_ID="sender_id";
	private static final String KEY_RECEIVER_ID="receiver_id";
	private static final String KEY_ARRIVAL_TIME="arrival_time";
	private static final String KEY_MESSAGE_TEXT="message_text";

	String CREATE_REQUEST_TABLE="CREATE TABLE "+ TABLE_DONATION_REQUEST
			+"("
			+ KEY_ID +" INTEGER PRIMARY KEY  , "
			+ KEY_SENDER_ID +" INTEGER, "
			+ KEY_RECEIVER_ID +" INTEGER, "
			+ KEY_ARRIVAL_TIME +" DATETIME, "
			+ KEY_MESSAGE_TEXT +" TEXT, "
			+ KEY_STATUS +" INTEGER, "
			+" FOREIGN KEY ("+KEY_SENDER_ID+") REFERENCES "+TABLE_USER+"("+KEY_ID+") ,"
			+" FOREIGN KEY ("+KEY_RECEIVER_ID+") REFERENCES "+TABLE_DONOR+"("+KEY_ID+") "
			+")";

	//MedDoc
	private static final String KEY_DONOR_ID="donor_id";
	private static final String KEY_DOC_TYPE="doc_type";
	private static final String KEY_IMAGE="image";

	String CREATE_MED_DOC_TABLE="CREATE TABLE "+ TABLE_MED_DOC
			+"("
			+ KEY_ID +" INTEGER PRIMARY KEY  , "
			+ KEY_DONOR_ID +" INTEGER, "
			+ KEY_DOC_TYPE +" INTEGER, "
			+ KEY_IMAGE +" BLOB, "
			+" FOREIGN KEY ("+KEY_DONOR_ID+") REFERENCES "+TABLE_DONOR+"("+KEY_ID+") "
			+")";


	String CREATE_POST_TABLE="CREATE TABLE POST " +
            "(PostId INTEGER PRIMARY KEY," +
            "UserId INTEGER," +
            "ReqGroup TEXT," +
            "Location TEXT," +
            "PostBody TEXT," +
            "PostStatus INTEGER," +
            "PostTime DATETIME ," +
            " FOREIGN KEY (UserId) REFERENCES USER(UserId) ON DELETE CASCADE"+
            ")";


    //BloodBank
    private static final String KEY_NAME="Name";

    String CREATE_BloodBank_TABLE="CREATE TABLE "+ TABLE_BloodBank
            +"("
            + KEY_ID +" INTEGER PRIMARY KEY  , "
            + KEY_NAME +" TEXT, "
            + KEY_EMAIL +" TEXT, "
            + KEY_CONTACTNO +" TEXT, "
            + KEY_LOCATION +" TEXT "
            +")";


	String CREATE_CITY_TABLE="CREATE TABLE "+ TABLE_City
			+"("
			+ KEY_ID +" INTEGER PRIMARY KEY  , "
			+ KEY_NAME +" TEXT "
			+")";

	public DatabaseHandler(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//Log.v("db created", "yes");


		db.execSQL(CREATE_USER_TABLE);
		db.execSQL(CREATE_DONOR_TABLE);
		db.execSQL(CREATE_REQUEST_TABLE);
		db.execSQL(CREATE_MED_DOC_TABLE);
        db.execSQL(CREATE_BloodBank_TABLE);
        db.execSQL(CREATE_POST_TABLE);
		db.execSQL(CREATE_CITY_TABLE);

		//Log.v("db created", "yes");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_USER);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_DONOR);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_DONATION_REQUEST);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_MED_DOC);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_BloodBank);
        db.execSQL("DROP TABLE IF EXISTS POST");
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_City);

		onCreate(db);
		//setCityTable();
	}

    public void setCityTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] cityArray = new String[]{"Dhaka","Chittagong","Rajshahi","Khulna","Sylhet","Barishal","Rangpur"};

        for (String city:cityArray) {
            ContentValues values=new ContentValues();
            values.put(KEY_NAME,city);
            db.insert(TABLE_City, null,values);
        }
        db.close();
    }
    
	public boolean checkExistance(String user_id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String check = "SELECT EMAIL FROM "+TABLE_USER+" WHERE EMAIL='"+user_id+"'";

		Cursor cursor=db.rawQuery(check, null);
		if(cursor.moveToFirst())
		{
			cursor.close();
			db.close();
			return true;
		}
		return false;
	}
	public boolean addUser(User user)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		if(checkExistance(user.getEmail()))
		{
			db.close();
			return false;
		}
		//String qry = "INSERT INTO CONTACT(NAME,PHONENO)VALUES('XYZ','016')";
		//db.execSQL(query);
		ContentValues value=new ContentValues();

		value.put(KEY_EMAIL, user.getEmail());
		value.put(KEY_PASSWORD, user.getPassword());
		value.put(KEY_STATUS,0);

		db.insert(TABLE_USER, null,value);

		db.close();
		return true;
		
	}

	public User getAuthentication(User user)
	{
		//User loggedInUser=null;
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT * FROM USER WHERE EMAIL='"+user.getEmail()+"' AND PASSWORD='"+user.getPassword()+"' AND status!=404";
		Cursor cursor=db.rawQuery(query, null);

		//int status=99;
		if(cursor.moveToFirst())
		{
			int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
			int st=cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
			User myUser=new User(id,st);
			//Log.d("userStatus", "status value: "+cursor.getString(cursor.getColumnIndex(KEY_FNAME)));
		//	status=cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
		//	Log.d("userStatus", "status value: "+status);
			cursor.close();
			db.close();
			return myUser;
		}
		return null;
	}

	public User getProfileInfo(String userEmail)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT * FROM USER WHERE EMAIL = '"+userEmail+"'";

		Cursor cursor=db.rawQuery(query, null);
		//Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID,KEY_NAME,KEY_CONTACTNO}, "Id=?",new String[]{String.valueOf(id)} ,null, null,null, null);
		User user = null;
		if(cursor.moveToFirst())
		{
			int user_id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
			String user_email=cursor.getString(cursor.getColumnIndex(KEY_EMAIL));

			String fname=cursor.getString(cursor.getColumnIndex(KEY_FNAME));
			String lname=cursor.getString(cursor.getColumnIndex(KEY_LNAME));

			String gender=cursor.getString(cursor.getColumnIndex(KEY_GENDER));
			String phone=cursor.getString(cursor.getColumnIndex(KEY_CONTACTNO));
			String photo=cursor.getString(cursor.getColumnIndex(KEY_CONTACTNO));
            String blood_group=cursor.getString(cursor.getColumnIndex(KEY_BLOOD_GROUP));

            String location=cursor.getString(cursor.getColumnIndex(KEY_LOCATION));
			String district=cursor.getString(cursor.getColumnIndex(KEY_DISTRICT));
			String police_station=cursor.getString(cursor.getColumnIndex(KEY_POLICE_STATION));
			int status=cursor.getInt(cursor.getColumnIndex(KEY_STATUS));

			String password=cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));

		//	(String firstName, String lastName, String phoneNumber, String gender, String photoUrl, String bloodGroup, String district, String police_station, String location, String status, String email, String password)
			user=new User(user_id,fname,lname,phone,blood_group,gender,photo,district,police_station,location,status,userEmail,password);

			cursor.close();
			db.close();
			return user;
		}
		cursor.close();
		db.close();
		return null;
	}


	public int updateUserProfile(User user) {


		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_FNAME, user.getFirstName());
		values.put(KEY_LNAME, user.getLastName());
		values.put(KEY_GENDER, user.getGender());
		values.put(KEY_BLOOD_GROUP,user.getBlood_group());
		values.put(KEY_CONTACTNO, user.getPhoneNumber());

		values.put(KEY_PHOTO_URL, user.getPhotoUrl());

        values.put(KEY_DISTRICT, user.getDistrict());
		values.put(KEY_POLICE_STATION, user.getPolice_station());
		values.put(KEY_LOCATION, user.getLocation());
		values.put(KEY_STATUS, 1);


		// updating row
		return db.update(TABLE_USER, values, KEY_EMAIL + " = ?",
				new String[] { user.getEmail() });
	}
//Changing Email and Password
	public int updateProfileCredential(String email, String new_email, String password) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		if(email.equals(new_email))
		{
			values.put(KEY_PASSWORD, password);
		}
		else if(checkExistance(new_email))
		{
			return 0;
		}
		else{
			values.put(KEY_PASSWORD, password);
			values.put(KEY_EMAIL, new_email);
		}
		// updating row
		return db.update(TABLE_USER, values, KEY_EMAIL + " = ?",
				new String[] { email });
	}
	// Deleting single user
	public void deleteUserAccount(String userEmail) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_STATUS,404);

		db.update(TABLE_USER, values, KEY_EMAIL + " = ?",
				new String[] { userEmail});
		db.close();
	}

	public boolean addDonor(String user_email)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String check = "SELECT * FROM "+TABLE_USER+" WHERE "+KEY_EMAIL+"='"+user_email+"' AND STATUS=1";

		Cursor cursor=db.rawQuery(check, null);
		int id=0;
		if(cursor.moveToFirst())
		{
			id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
			ContentValues value=new ContentValues();
			ContentValues statusValue=new ContentValues();
			statusValue.put(KEY_STATUS,2);

			value.put(KEY_ID,id);
			value.put(KEY_RANK_ID,0);
			value.put(KEY_VISIBILITY,0);
			value.put(KEY_DONATION_COUNT,0);
			value.put(KEY_LAST_DONATION_DATE," ");
			//value.put(KEY_PASSWORD, donor.getPassword());

			SQLiteDatabase liteDatabase=getWritableDatabase();
			liteDatabase.insert(TABLE_DONOR, null,value);

			liteDatabase.update(TABLE_USER,statusValue,KEY_EMAIL + " = ?",
					new String[] { user_email });

			db.close();
			liteDatabase.close();
			return true;
		}
		return false;
	}

	public List<Donor> getAllDonor(String requiredGroup, String location,String ps,String dist,int id)
	{
		List<Donor> myDonorList=new ArrayList<Donor>();
		String query = "SELECT * FROM User,Donor WHERE  USER.id=Donor.id AND visibility=1 AND status!=404 AND blood_group = '"+requiredGroup+"' AND Donor.id != "+id+
				" AND location = '"+location+"' AND district = '"+dist+"'";
/*
		String query = "SELECT * FROM User,Donor WHERE  USER.id=Donor.id AND visibility=1 AND status!=404 AND blood_group = '"+requiredGroup+"' AND Donor.id != "+id+
				" AND location = '"+location+"' AND  police_station= '"+ps+"' AND district = '"+dist+"'";
		*/
		//String query = "SELECT * FROM DONORS WHERE blooddgroup = '"+requiredGroup+"' AND address = '"+Location+"'";

		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query, null);

		if(cursor.moveToFirst())
		{
			do
			{
				int donor_id=cursor.getInt(cursor.getColumnIndex(KEY_ID));

				String user_email=cursor.getString(cursor.getColumnIndex(KEY_EMAIL));

				String fname=cursor.getString(cursor.getColumnIndex(KEY_FNAME));
				String lname=cursor.getString(cursor.getColumnIndex(KEY_LNAME));
                String phone=cursor.getString(cursor.getColumnIndex(KEY_CONTACTNO));
                String gender=cursor.getString(cursor.getColumnIndex(KEY_GENDER));
                String photoUrl=cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL));

                String district=cursor.getString(cursor.getColumnIndex(KEY_DISTRICT));
                String station=cursor.getString(cursor.getColumnIndex(KEY_POLICE_STATION));
            //    String location=cursor.getString(cursor.getColumnIndex(KEY_LOCATION));

                int status=cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
                String password=cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));

                String blood_group=cursor.getString(cursor.getColumnIndex(KEY_BLOOD_GROUP));
            /*
                int count=0;
                int visibility=1;
                int rank=0;
                String last_donation_date="null";
                */
                 int count=cursor.getInt(cursor.getColumnIndex(KEY_DONATION_COUNT));
                 int visibility=cursor.getInt(cursor.getColumnIndex(KEY_VISIBILITY));
                 int rank=cursor.getInt(cursor.getColumnIndex(KEY_RANK_ID));
                 String last_donation_date=cursor.getString(cursor.getColumnIndex(KEY_LAST_DONATION_DATE));

                Donor myDonor=new Donor(donor_id,fname,lname,phone,blood_group,gender,photoUrl,district,station,location,status,user_email,password, count,visibility,rank,last_donation_date);
				// myDonor=new User(donor_id,fname,lname,phone,blood_group,gender,photoUrl,district,station,location,status,user_email,password);
				myDonorList.add(myDonor);

			}while(cursor.moveToNext());
		}
		int s=myDonorList.size();
		Log.d("size", "getAllDonor: "+s);
		return myDonorList;
	}
	//Changing flag
	public int updateDonationFlag(String email, int visibility) {
        SQLiteDatabase rdb = this.getReadableDatabase();

        String check = "SELECT EMAIL FROM "+TABLE_USER+" WHERE EMAIL='"+email+"'";
		String query = "SELECT * FROM DONOR WHERE id=(Select id from USER where email = '"+email+"')";

        Cursor cursor=rdb.rawQuery(query, null);

		if(cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
			cursor.close();
			rdb.close();

			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(KEY_VISIBILITY, visibility);
			// updating row
			return db.update(TABLE_DONOR, values, KEY_ID + " = ?",
					new String[] { String.valueOf(id)});
		}
		return 0;
	}
	public int getVisibilityStatus(String email)
	{
		SQLiteDatabase rdb = this.getReadableDatabase();

		String query = "SELECT * FROM DONOR WHERE id=(Select id from USER where email = '"+email+"')";
		Cursor cursor=rdb.rawQuery(query, null);
		if(cursor.moveToFirst()) {
			int status = cursor.getInt(cursor.getColumnIndex(KEY_VISIBILITY));
			cursor.close();
			rdb.close();
			return  status;
		}
		return 0;
	}

	public int insertImage(byte[] image)
	{
		return 0;
	}

	public Donor getDonorInfo(int id) {
		SQLiteDatabase rdb = this.getReadableDatabase();
		String query = "SELECT * FROM User,Donor WHERE  USER.id=Donor.id AND Donor.id = "+id+"";
		Cursor cursor=rdb.rawQuery(query, null);

		if(cursor.moveToFirst()) {
			int donor_id=cursor.getInt(cursor.getColumnIndex(KEY_ID));

			String user_email=cursor.getString(cursor.getColumnIndex(KEY_EMAIL));

			String fname=cursor.getString(cursor.getColumnIndex(KEY_FNAME));
			String lname=cursor.getString(cursor.getColumnIndex(KEY_LNAME));
			String phone=cursor.getString(cursor.getColumnIndex(KEY_CONTACTNO));
			String gender=cursor.getString(cursor.getColumnIndex(KEY_GENDER));
			String photoUrl=cursor.getString(cursor.getColumnIndex(KEY_PHOTO_URL));
			String district=cursor.getString(cursor.getColumnIndex(KEY_DISTRICT));
			String station=cursor.getString(cursor.getColumnIndex(KEY_POLICE_STATION));
			String location=cursor.getString(cursor.getColumnIndex(KEY_LOCATION));
			int status=cursor.getInt(cursor.getColumnIndex(KEY_STATUS));
			String password=cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
			String blood_group=cursor.getString(cursor.getColumnIndex(KEY_BLOOD_GROUP));
			int count=cursor.getInt(cursor.getColumnIndex(KEY_DONATION_COUNT));
			int visibility=cursor.getInt(cursor.getColumnIndex(KEY_VISIBILITY));
			int rank=cursor.getInt(cursor.getColumnIndex(KEY_RANK_ID));
			String last_donation_date=cursor.getString(cursor.getColumnIndex(KEY_LAST_DONATION_DATE));
			Donor myDonor=new Donor(donor_id,fname,lname,phone,blood_group,gender,photoUrl,district,station,location,status,user_email,password, count,visibility,rank,last_donation_date);
			return myDonor;
		}
		return null;
	}

    public List<BloodBank> getBloodBankList()
    {
        List<BloodBank> myBBList=new ArrayList<BloodBank>();


        String query = "SELECT * FROM BloodBank";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst())
        {
            do
            {
                int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
                String email=cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
                String contactNo=cursor.getString(cursor.getColumnIndex(KEY_CONTACTNO));
                String location=cursor.getString(cursor.getColumnIndex(KEY_LOCATION));

                BloodBank bloodBank=new BloodBank(id,name,email,location,contactNo);

                myBBList.add(bloodBank);

            }while(cursor.moveToNext());
        }
        int s=myBBList.size();
        Log.d("size", "getAllDonor: "+s);
        return myBBList;
    }

	public List<String> getCityList()
	{
		List<String> myCityList=new ArrayList<String>();


		String query = "SELECT * FROM City";

		SQLiteDatabase db=this.getReadableDatabase();

		Cursor cursor=db.rawQuery(query, null);

		if(cursor.moveToFirst())
		{
			do
			{
				int id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
				String name=cursor.getString(cursor.getColumnIndex(KEY_NAME));

				myCityList.add(name);

			}while(cursor.moveToNext());
		}
		int s=myCityList.size();
		Log.d("size", "getAllCity: "+s);
		return myCityList;
	}
}
