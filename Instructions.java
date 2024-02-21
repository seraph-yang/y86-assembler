public class Instructions {
    public static String[] registers = new String[16];

    public Instructions(){
        populate();
    }

    private void populate(){
        registers[0] = "0000";
        registers[1] = "0001";
        registers[2] = "0010";
        registers[3] = "0011";
        registers[4] = "0100";
        registers[5] = "0101";
        registers[6] = "0110";
        registers[7] = "0111";
        registers[8] = "1000";
        registers[9] = "1001";
        registers[10] = "1010";
        registers[11] = "1011";
        registers[12] = "1100";
        registers[13] = "1101";
        registers[14] = "1110";
        registers[15] = "1111";//TODO omit this
    }

}
