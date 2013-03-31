package me.tgic.phonenumgeo;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: tgic
 * Date: 3/31/13
 * Time: 8:37 PM
 */
public class FilePhonenumDataLoader {

    private static final int BLOCK_SIZE = 8;
    private static final int COUNT = 44399;

    static class PhoneNumberStruct {
        int number;
        short range;
        short cityid;
    }

    private PhoneNumberStruct[] sts = new PhoneNumberStruct[COUNT];

    public void init(final InputStream is) throws IOException {
        try {
            for(int i = 0; i< COUNT; i++){
                byte[] buff = new byte[BLOCK_SIZE];
                is.read(buff);
                DataInputStream dais = new DataInputStream(new ByteArrayInputStream(buff));;
                sts[i] = new PhoneNumberStruct();
                // DONT CHANGE ORDER
                sts[i].number = dais.readInt();
                sts[i].range = dais.readShort();
                sts[i].cityid = dais.readShort();
            }
        }finally {
            is.close();
        }
    }


    private PhoneNumberStruct search(int number){

        PhoneNumberStruct phoneNumberStruct = new PhoneNumberStruct();
        phoneNumberStruct.number = number;

        int index = Arrays.binarySearch(sts, phoneNumberStruct, new Comparator<PhoneNumberStruct>() {
            @Override
            public int compare(PhoneNumberStruct lhs, PhoneNumberStruct rhs) {

                int diff = lhs.number - rhs.number;

                if((rhs.number <= (lhs.number + lhs.range)) && diff < 0){
                    return 0;
                }else
                if((lhs.number <= (rhs.number + rhs.range)) && diff > 0){
                    return 0;
                }else {
                    return diff;
                }
            }
        });

        if(index >= 0)
            return sts[index];
        else
            return null;
    }

    public String searchGeocode(String number){
        try {
            int _number = (int) (Long.parseLong(number) / 10000 - 1300000L);

            PhoneNumberStruct st = search(_number);

            if( st != null ){
                return CityNames.idToName( st.cityid);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {


        FilePhonenumDataLoader filePhonenumDataLoader = new FilePhonenumDataLoader();
        FileInputStream file = new FileInputStream(new File("/tmp/out.b"));
        filePhonenumDataLoader.init(file);

        System.out.println(filePhonenumDataLoader.searchGeocode("18910228396"));

//        PhoneNumberStruct st = filePhonenumDataLoader.sts[44];
//        st = filePhonenumDataLoader.search(171);
//        System.out.println(
//                        st.number + 1300000
//        );
//        System.out.println(
//                CityNames.idToName(
//        st.cityid)
//                );

    }
}
