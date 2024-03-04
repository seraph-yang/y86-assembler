import java.util.BitSet;

public class Instructions {
    public static String[] aRegisters = new String[16];//registers names in assembly (string names)
    public static long[] registers = new long[16];//registers NOTE: long is 64 bits!!
    public static BitSet flags = new BitSet(3);//a set of bits to represent our flags!
        //flags(0) is ZF
        //flags(1) is SF
        //flags(2) is OF

    public List<Integer> bytes;
    public int index = 0;
    public int alignment = 8;

    //TODO should we add program status here or in program counter? 
    //i think it should either be an enum or a bitset

    public Instructions() {
        this.bytes = new ArrayList<Integer>();
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
        aRegisters[15] = "r15";//indicates no register

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
        registers[15] = "1111";//indicates no register
        */
    }

    public void pos(int x) {
        for(int j = this.index; j < i; j++) {
            if(this.bytes.size() <= j) {
                this.bytes.add(j, 0);
            }
        }
        this.index = 1;
    }

    public void align(int x) {
        this.alignment = x;
    }

    public void long(long x) {
        long copy = x;
        Array<List> digits = new ArrayList<Integer>();

        while(temp > 0) {
            digits.add(0, (int) temp % 256);
            temp /= 256;
        }

        for(Integer digit:digits) {
            this.bytes.add(this.index, digit);
            this.index++;
        }
    }

    public int merge(int a, int b) {
        return (a << 4) + b;
    }

    //TODO should dests be long??
    public static void halt(){
        this.bytes.add(this.index, (0x00));
        this.index++;
    }

    public static void nop(){
        this.bytes.add(this.index, (0x10));
        this.index++;
    }
    // le-1
    // l-2
    // e-3
    // ne-4
    // ge-5
    // g-6
    public static void cmovle(){
        this.bytes.add(this.index, merge(0x2, 1));
        this.index++;
    }
    public static void cmovl() {
        this.bytes.add(this.index, merge(0x2, 2));
        this.index++;
    }
    public static void cmove() {
        this.bytes.add(this.index, merge(0x2, 3));
        this.index++;
    }    
    public static void cmovne() {
        this.bytes.add(this.index, merge(0x2, 4));
        this.index++;
    }
    public static void cmovge() {
        this.bytes.add(this.index, merge(0x2, 5));
        this.index++;
    }
    public static void cmovg() {
        this.bytes.add(this.index, merge(0x2, 6));
        this.index++;
    }

    //cmovXX
    public static void rrmovq(int rA, int rB){//TODO should register parameters be strings?? 
        //i think we should have a method to convert from names to indices before these methods
        //depends on fetch/execute code
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
    
    //jXX
    public static void jmp(int d){

    }
    public static void jle(int d){

    }
    public static void jl(int d){

    }
    public static void je(int d){

    }
    public static void jne(int d){

    }
    public static void jge(int d){

    }
    public static void jg(int d){

    }

    public static void call(int d){

    }
    public static void ret(){
        this.bytes.add(this.index, (0x90));
        this.index++;
    }
    public static void pushq(String rA){

    }
    public static void popq(String rA){

    }
}
