package movies.flag.pt.moviesapp.databases;
import android.os.Parcel;
import android.os.Parcelable;
import com.orm.SugarRecord;

/**
 * DB Entity to save Favorite Movies
 */

public class FavoritesDb extends SugarRecord implements Parcelable {
    public int mItemId;
    public String mTitle;
    public Double mVoteAverage;
    public String mPosterPath;
    public String mReleaseDate;
    public String mOverview;

    public FavoritesDb(){}

    public FavoritesDb (Integer mItemId, String mTitle, Double mVoteAverage, String mPosterPath,
                        String mReleaseDate, String mOverview) {
        this.mItemId = mItemId;
        this.mTitle = mTitle;
        this.mVoteAverage = mVoteAverage;
        this.mPosterPath = mPosterPath;
        this.mReleaseDate = mReleaseDate;
        this.mOverview = mOverview;
    }

    public int getItemId() {
        return mItemId;
    }
    public String getTitle() {
        return mTitle;
    }
    public Double getVoteAverage() {
        return mVoteAverage;
    }
    public String getPosterPath() {
        return mPosterPath;
    }
    public String getReleaseDate() {
        return mReleaseDate;
    }
    public String getOverview() {
        return mOverview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mItemId);
        dest.writeString(this.mTitle);
        dest.writeDouble(this.mVoteAverage);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mOverview);
    }

    private FavoritesDb(Parcel in) {
        this.mItemId = in.readInt();
        this.mTitle = in.readString();
        this.mVoteAverage = in.readDouble();
        this.mPosterPath = in.readString();
        this.mReleaseDate = in.readString();
        this.mOverview = in.readString();
    }

    public static final Creator<FavoritesDb> CREATOR = new Creator<FavoritesDb>() {
        @Override
        public FavoritesDb createFromParcel(Parcel source) {
            return new FavoritesDb(source);
        }

        @Override
        public FavoritesDb[] newArray(int size) {
            return new FavoritesDb[size];
        }
    };
}

