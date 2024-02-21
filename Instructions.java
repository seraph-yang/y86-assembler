import java.util.BitSet;

public class Instructions {
    public static String[] aRegisters = new String[16];//registers names in assembly (string names)
    public static long[] registers = new long[16];//registers NOTE: long is 64 bits!!
    public static BitSet flags = new BitSet(3);//a set of bits to represent our flags!
        //flags(0) is ZF
        //flags(1) is SF
        //flags(2) is OF

    public Instructions(){
        populate();
        flags.clear(); //sets all flags to false
    }

    private void populate(){
        aRegisters[0] = "rax";//TODO include %??
        aRegisters[1] = "rcx";
        aRegisters[2] = "rdx";
        aRegisters[3] = "rbx";
        aRegisters[4] = "rsp";
        aRegisters[5] = "rbp";
        aRegisters[6] = "rsi";
        aRegisters[7] = "rdi";
        aRegisters[8] = "r8";
        aRegisters[9] = "r9";
        aRegisters[10] = "r10";
        aRegisters[11] = "r11";
        aRegisters[12] = "r12";
        aRegisters[13] = "r13";
        aRegisters[14] = "r14";
        aRegisters[15] = "r15";//TODO omit this

        //how to output register names into machine code? 
       /* registers[0] = "0000";
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
        */
    }

    //TODO make these
    //TODO should dests be long??
    public static void halt(){

    }
    public static void nop(){

    }
    public static void cmovXX(String rA, String rB){//TODO should register parameters be strings?? depends on fetch/execute code

    }
    public static void irmovq(int V, String rB){//TODO what is v??? address in memory??

    }
    public static void rmmovq(String rA, String rB, int d){// TODO do we need both rB and D??

    }
    public static void mrmovq(String rA, String rB, int d){

    }

    //OPq
    public static void addq(String rA, String rB){

    }
    public static void subq(String rA, String rB){

    }
    public static void andq(String rA, String rB){

    }
    public static void xorq(String rA, String rB){

    }
    
    public static void jXX(int d){

    }
    public static void call(int d){

    }
    public static void ret(){

    }
    public static void pushq(String rA){

    }
    public static void popq(String rA){

    }
}
