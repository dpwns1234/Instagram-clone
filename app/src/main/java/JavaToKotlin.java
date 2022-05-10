//import android.database.Cursor;
//import android.net.Uri;
//import android.provider.MediaStore;
//
//public class JavaToKotlin {
//
//    public static String getFullPathFromUri(Context ctx, Uri fileUri) {
//        String fullPath = null;
//        final String column = "_data";
//        Cursor cursor = ctx.getContentResolver().query(fileUri, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            String document_id = cursor.getString(0);
//            if (document_id == null) {
//                for (int i=0; i < cursor.getColumnCount(); i++) {
//                    if (column.equalsIgnoreCase(cursor.getColumnName(i))) {
//                        fullPath = cursor.getString(i);
//                        break;
//                    }
//                }
//            } else {
//                document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
//                cursor.close();
//
//                final String[] projection = {column};
//                try {
//                    cursor = ctx.getContentResolver().query(
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                            projection, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
//                    if (cursor != null) {
//                        cursor.moveToFirst();
//                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column));
//                    }
//                } finally {
//                    if (cursor != null) cursor.close();
//                }
//            }
//        }
//        return fullPath;
//    }
//}
