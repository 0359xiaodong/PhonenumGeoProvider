package me.tgic.phonenumgeo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.text.TextUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tgic
 * Date: 3/31/13
 * Time: 2:36 AM
 */
public class PhonenumGeoProvider extends ContentProvider {

//    private static final String GEO_AUTHORITY = "com.android.i18n.phonenumbers.geocoding";
    private static final String[] COLUMN_NAMES = new String[]{"GEOCODE"};
    private FilePhonenumDataLoader filePhonenumDataLoader = new FilePhonenumDataLoader();

    @Override
    public boolean onCreate() {
        try {
            filePhonenumDataLoader.init(getContext().getResources().openRawResource(R.raw.phonenumber));
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        List<String> paths = uri.getPathSegments();
        if(paths !=null && paths.size() >= 2){
            String countryIso = paths.get(0);
            String number = paths.get(1);
            Cursor cursor = wrapGeoCodeToCursor(searchGeoCode(countryIso, number));
            return cursor;
        }else {
            return null;
        }
    }

    private Cursor wrapGeoCodeToCursor(String geocode){

        if( TextUtils.isEmpty(geocode) ){
            return null;
        }

        MatrixCursor cursor = new MatrixCursor(COLUMN_NAMES);
        cursor.addRow(new String[]{geocode});
        return cursor;
    }

    private String searchGeoCode(String countryIso,String number){

        if("CN".equals(countryIso)){
            return filePhonenumDataLoader.searchGeocode(number);
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}
