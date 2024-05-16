import java.io.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

class Main {
    static JPanel panel = new JPanel();
    static JFrame frame = new JFrame();
    static String file;

    static int[] R = new int[8];

    static int PC = 0;
    static int index = 0;

    static boolean cnd = false;

    static boolean ZF = false;
    static boolean SF = false;
    static boolean OF = false;

    static long[] memory = new long[1024];

    static JLabel header = new JLabel("Loop 0");
    static JLabel pc = new JLabel("newPC: ");
    static JLabel memoryLabel = new JLabel("valM: ");

    static JLabel eax = new JLabel("%eax: ");
    static JLabel ecx = new JLabel("%ecx: ");
    static JLabel edx = new JLabel("%edx: ");
    static JLabel ebx = new JLabel("%ebx: ");
    static JLabel esp = new JLabel("%esp: ");
    static JLabel ebp = new JLabel("%ebp: ");
    static JLabel esi = new JLabel("%esi: ");
    static JLabel edi = new JLabel("%edi: ");

    static JLabel decodeA = new JLabel("valA: ");
    static JLabel decodeB = new JLabel("valB: ");
    static JLabel decodeE = new JLabel("valE: ");

    static JLabel executeLabel = new JLabel("valE: ");

    static JLabel ZFLabel = new JLabel("ZF: ");
    static JLabel SFLabel = new JLabel("SF: ");
    static JLabel OFLabel = new JLabel("OF: ");

    static JLabel fetchFun = new JLabel("ifun: ");
    static JLabel fetchCode = new JLabel("icode: ");
    static JLabel fetchrA = new JLabel("rA: ");
    static JLabel fetchrB = new JLabel("rB: ");
    static JLabel fetchValC = new JLabel("valC: ");
    static JLabel fetchValP = new JLabel("valP: ");

    public static void main(String[] args) throws IOException, InterruptedException {
        String pathIn = new File("in.txt").getAbsolutePath(); // Input file
        file = readFile(pathIn);

        frame.setSize(1500, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);

        JLabel headerTitle = new JLabel();
        headerTitle.setText("<html><h1><font color=BLUE>PC Update Stage</font></h1></html>");

        JLabel memoryTitle = new JLabel();
        memoryTitle.setText("<html><h1><font color=BLUE>Memory Stage</font></h1></html>");

        JLabel registerTitle = new JLabel();
        registerTitle.setText("<html><h1><font color=BLUE>Register File</font></h1></html>");

        JLabel executeTitle = new JLabel();
        executeTitle.setText("<html><h1><font color=BLUE>Execute Stage</font></h1></html>");

        JLabel decodeTitle = new JLabel();
        decodeTitle.setText("<html><h1><font color=BLUE>Decode Stage</font></h1></html>");

        JLabel conditionCodesTitle = new JLabel();
        conditionCodesTitle.setText("<html><h1><font color=BLUE>Condition Codes</font></h1></html>");

        JLabel fetchTitle = new JLabel();
        fetchTitle.setText("<html><h1><font color=BLUE>Fetch Stage</font></h1></html>");

        header.setBounds(10, 10, 100, 20);
        panel.add(header);

        headerTitle.setBounds(10, 60, 400, 30);
        panel.add(headerTitle);
        pc.setBounds(10, 90, 100, 20);
        panel.add(pc);

        registerTitle.setBounds(10, 140, 400, 30);
        panel.add(registerTitle);

        eax.setBounds(10, 170, 100, 20);
        ecx.setBounds(10, 190, 100, 20);
        edx.setBounds(10, 210, 100, 20);
        ebx.setBounds(10, 230, 100, 20);
        esp.setBounds(10, 250, 100, 20);
        ebp.setBounds(10, 270, 100, 20);
        esi.setBounds(10, 290, 100, 20);
        edi.setBounds(10, 310, 100, 20);

        panel.add(eax);
        panel.add(ecx);
        panel.add(edx);
        panel.add(ebx);
        panel.add(esp);
        panel.add(ebp);
        panel.add(esi);
        panel.add(edi);

        executeTitle.setBounds(10, 340, 400, 30);
        panel.add(executeTitle);
        executeLabel.setBounds(10, 370, 400, 30);
        panel.add(executeLabel);

        decodeTitle.setBounds(10, 400, 400, 30);
        panel.add(decodeTitle);
        decodeA.setBounds(10, 430, 400, 30);
        panel.add(decodeA);
        decodeB.setBounds(10, 450, 400, 30);
        panel.add(decodeB);
        decodeE.setBounds(10, 470, 400, 30);
        panel.add(decodeE);

        memoryTitle.setBounds(10, 500, 400, 30);
        panel.add(memoryTitle);
        memoryLabel.setBounds(10, 530, 100, 20);
        panel.add(memoryLabel);

        fetchTitle.setBounds(10, 560, 400, 30);
        panel.add(fetchTitle);
        fetchFun.setBounds(10, 590, 400, 30);
        fetchCode.setBounds(10, 610, 400, 30);
        fetchrA.setBounds(10, 630, 400, 30);
        fetchrB.setBounds(10, 650, 400, 30);
        fetchValC.setBounds(10, 670, 400, 30);
        fetchValP.setBounds(10, 690, 400, 30);
        panel.add(fetchFun);
        panel.add(fetchCode);
        panel.add(fetchrA);
        panel.add(fetchrB);
        panel.add(fetchValC);
        panel.add(fetchValP);

        conditionCodesTitle.setBounds(10, 720, 400, 30);
        panel.add(conditionCodesTitle);
        ZFLabel.setBounds(10, 740, 400, 30);
        SFLabel.setBounds(10, 760, 400, 30);
        OFLabel.setBounds(10, 780, 400, 30);
        panel.add(ZFLabel);
        panel.add(OFLabel);
        panel.add(SFLabel);

        panel.setLayout(null);

        process();
    }

    static String readFile(String pathIn) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(pathIn));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString().replaceAll("\\s+","");
        } finally {
            br.close();
        }
    }

    public static void process() throws InterruptedException {
        int num = 1;
        while(index < file.length()) {
            TimeUnit.SECONDS.sleep(2);
            printHeader(num);
            long[] values = fetch();
            values = decode(values);
            values = execute(values);
            values = memory(values);
            writeback(values);
            updateProgramCounter(values);
            num++;
        }
    }
    
    public static String byteReverse(String n) {
        String out = "";
        for(int i = 0; i < n.length(); i+=2) {
            String token = n.substring(i, i+2);
            out = token + out;
        }
        return out;
    }

    public static int read() {
        int word = Character.getNumericValue(file.charAt(index));
        index++;
        return word;
    }

    public static long readNext() {
        String value = file.substring(index, index+16);
        index += 16;
        return Long.decode("0x" + byteReverse(value));
    }

    public long readNext(int otherIndex) {
        String value = file.substring(otherIndex, otherIndex+16);
        return Long.decode("0x" + byteReverse(value));
    }

    public static void write(long i, long val) {
        memory[(int) i] = val;
    }

    public static long readNextStack(int i) {
        return memory[(int) i];
    }

    public static void printHeader(int num) {
        header.setText("Loop " + num);
    }

    public static void printFetch(long ifun, long icode, long rA, long rB, long valP, long valC) {
        fetchFun.setText("ifun: " + ifun);
        fetchCode.setText("icode: " + icode);
        fetchrA.setText("rA: " + rA);
        fetchrB.setText("rB: " + rB);
        fetchValC.setText("valC: " + valC);
        fetchValP.setText("valP: " + valP);
    }

    public static void printDecode(long valA, long valB, long valE) {
        decodeA.setText("valA: " + valA);
        decodeB.setText("valB: " + valB);
        decodeE.setText("valE: " + valE);
    }

    public static void printExecute(long valE, boolean ZF, boolean OF, boolean SF) {
        executeLabel.setText("valE: " + valE);
        ZFLabel.setText("ZF: " + ZF);
        SFLabel.setText("SF: " + SF);
        OFLabel.setText("OF: " + OF);
    }

    public static void printMemory(long valM) {
        memoryLabel.setText("valM: " + valM);
    }

    public static void printWriteback(int[] R) {
        eax.setText("%eax: " + R[0]);
        ecx.setText("%ecx: " + R[1]);
        edx.setText("%edx: " + R[2]);
        ebx.setText("%ebx: " + R[3]);
        esp.setText("%esp: " + R[4]);
        ebp.setText("%ebp: " + R[5]);
        esi.setText("%esi: " + R[6]);
        edi.setText("%edi: " + R[7]);
    }

    public static void printPC(int PC) {
        pc.setText("newPC: " + PC);
    }

    public static long[] fetch() {
        int icode = read();
        int ifun = -1;
        long valC = -1;
        int valP = -1;
        int valA = -1;
        int valB = -1;
        int valE = -1;
        int rA = -1;
        int rB = -1;
        
        ifun = read();

        if (icode == 0) { //halt
            System.out.println("Done");
            System.exit(0);
        } else if (icode == 1) { //nop
            // Do nothing
        } else if (icode == 2 || icode == 3 || icode == 4 || icode == 5 || icode == 6 || icode == 10 || icode == 11) { //rrmovq, irmovq, rmmovq, mrmovq, OPq, pushq, popq
            rA = read();
            rB = read();
            valP = PC + ((icode == 2 || icode == 3 || icode == 4 || icode == 5 || icode == 6) ? 10 : 2);
            if (icode == 3 || icode == 4 || icode == 5) {
                valC = readNext();
            }
        } else if (icode == 7 || icode == 8) { //jXX, call
            valC = readNext();
            valP = PC + 9;
        } else if (icode == 9) { //ret
            valP = PC + 1;
        }

        printFetch(ifun, icode, rA, rB, valP, valC);
        long[] values = {icode, ifun, valC, valP, valA, valB, valE, rA, rB};
        return values;
    }

    public static long[] decode(long[] input) {
        long icode = input[0];
        long ifun = input[1];
        long valC = input[2];
        long valP = input[3];
        long valA = input[4];
        long valB = input[5];
        long valE = input[6];
        long rA = input[7];
        long rB = input[8];

        if (icode == 0 || icode == 1) { //halt, nop
            // Do nothing
        } else if (icode == 2) { //rrmovq
            valA = R[(int) rA];
        } else if (icode == 3) { //irmovq
            //nothing?
        } else if (icode == 4) { //rmmovq
            valA = R[(int) rA];
            valB = R[(int) rB];
        } else if (icode == 5) { //mrmovq
            valB = R[(int) rB];
        } else if (icode == 6) { //OPq
            valA = R[(int) rA];
            valB = R[(int) rB];
        } else if (icode == 8 || icode == 9 || icode == 10 || icode == 11) { //call, ret, pushq, popq
            valA = (icode == 10 || icode == 11) ? R[4] : 0;
            valB = (icode == 8 || icode == 10) ? R[4] : 0;
        }

        printDecode(valA, valB, valE);
        long[] values = {icode, ifun, valC, valP, valA, valB, valE, rA, rB};
        return values;
    }

    public static long[] execute(long[] input) {
        long icode = input[0];
        long ifun = input[1];
        long valC = input[2];
        long valP = input[3];
        long valA = input[4];
        long valB = input[5];
        long valE = input[6];
        long rA = input[7];
        long rB = input[8];

        if (icode == 0) { // halt
            // Do nothing
        } else if (icode == 1) { // nop
            // Do nothing
        } else if (icode == 2) { // rrmovq
            valE = valB + valC;
        } else if (icode == 3) { // irmovq
            valE = 0 + valC;
        } else if (icode == 4) { // rmmovq
            valE = valB + valC;
        } else if (icode == 5) { // mrmovq
            valE = valB + valC;
        } else if (icode == 6) { // OPq
            long oldVal = valE;
            if (ifun == 0) {
                valE = valB + valA;
                // Check for overflow
                if (valB > 0 && valA > 0 && valE < 0) OF = true;
                else OF = false;
                if (valB < 0 && valA < 0 && valE > 0) OF = true;
                else OF = false;
            } else if (ifun == 1) {
                valE = valB - valA;
                // Check for overflow
                if (valB > 0 && valA < 0 && valE < 0) OF = true;
                else OF = false;
                if (valB < 0 && valA > 0 && valE > 0) OF = true;
                else OF = false;
            } else if (ifun == 2) {
                valE = valB & valA;
            } else if (ifun == 3) {
                valE = valB ^ valA;
            }
            if (valE == 0)
                ZF = true;
            else
                ZF = false;
            if (oldVal < 0) {
                if (valE > 0)
                    SF = true;
                else
                    SF = false;
            } else {
                if (valE < 0)
                    SF = true;
                else
                    SF = false;
            }
        } else if (icode == 7) { // jxx
            cnd = condition((int) ifun);
        } else if (icode == 8) { // call
            valE = valB - 8;
        } else if (icode == 9) { // return
            valE = valB + 8;
        } else if (icode == 10) { // pushq
            valE = valB - 8;
        } else if (icode == 11) { // popq
            valE = valB + 8;
        }

        printExecute(valE, ZF, OF, SF);
        long[] values = {icode, ifun, valC, valP, valA, valB, valE, rA, rB};
        return values;
    }

    public static long[] memory(long[] input) {
        // Update memory
        long icode = input[0];
        long ifun = input[1];
        long valC = input[2];
        long valP = input[3];
        long valA = input[4];
        long valB = input[5];
        long valE = input[6];
        long rA = input[7];
        long rB = input[8];
        long valM = -1;

        if (icode == 0) { // halt
            // Do nothing
        } else if (icode == 1) { // nop
            // Do nothing
        } else if (icode == 2) { // rrmovq
            // Do nothing
        } else if (icode == 3) { // irmovq
            // Do nothing
        } else if (icode == 4) { // rmmovq
            // M8[valE] <- valA
            write(valE, valA);
        } else if (icode == 5) { // mrmovq
            // valM <- M8[valE]
            valM = readNext();
        } else if (icode == 6) { // OPq
            // Do nothing
        } else if (icode == 7) { // jXX
            // Do nothing
        } else if (icode == 8) { // call
            write(valE, valP);
        } else if (icode == 9) { // ret
            valM = readNextStack((int) valA);
        } else if (icode == 10) { // pushq
            write(valE, valA);
        } else if (icode == 11) { // popq
            valM = readNext();
        }

        printMemory(valM);
        long[] values = {icode, ifun, valC, valP, valA, valB, valE, rA, rB, valM};
        return values;
    }

    public static void writeback(long[] input) {
        long icode = input[0];
        long ifun = input[1];
        long valC = input[2];
        long valP = input[3];
        long valA = input[4];
        long valB = input[5];
        long valE = input[6];
        long rA = input[7];
        long rB = input[8];
        long valM = input[9];

        if (icode == 0) { // halt
            // Do nothing
        } else if (icode == 1) { // nop
            // Do nothing
        } else if (icode == 2) { // rrmovq
            if (valE != -1) {
                R[(int) rB] = (int) valE;
            }
        } else if (icode == 3) { // irmovq
            if (valE != -1) {
                R[(int) rB] = (int) valE;
            }
        } else if (icode == 4) { // rmmovq
            // Do nothing
        } else if (icode == 5) { // mrmovq
            if (valM != -1) {
                R[(int) rA] = (int) valM;
            }
        } else if (icode == 6) { // OPq
            if (valE != -1) {
                R[(int) rB] = (int) valE;
            }
        } else if (icode == 7) { // jXX
            // Do nothing
        } else if (icode == 8) { // call
            R[4] = (int) valE;
        } else if (icode == 9) { // ret
            R[4] = (int) valE;
        } else if (icode == 10) { // pushq
            R[4] = (int) valE;
            if (valM != -1) {
                R[(int) rA] = (int) valM;
            }
        } else if (icode == 11) { // popq
            // Do nothing
        }

        printWriteback(R);
    }


    public static void updateProgramCounter(long[] input) {
        long icode = input[0];
        long valC = input[2];
        long valP = input[3];
        long valM = input[9];

        if (icode == 1) { // nop
            PC++;
        } else if (icode == 2 || icode == 3 || icode == 4 || icode == 5 || icode == 6) { // rrmovq, irmovq, rmmovq, mrmovq, OPq
            PC = (int) valP;
        } else if (icode == 7) { // jXX
            PC = cnd ? (int) valC : (int) valP;
        } else if (icode == 8 || icode == 10) { // call, pushq
            PC = (int) valC;
        } else if (icode == 9) { // ret
            PC = (int) valM;
        }

        index = PC * 2;
        printPC(PC);
    }


    public static boolean condition(int op) {
        if (op == 0) {
            return true;
        } else if (op == 1) { // le
            return (SF && !OF) || ZF;
        } else if (op == 2) { // l
            return (SF && !OF);
        } else if (op == 3) { // e
            return ZF;
        } else if (op == 4) { // ne
            return !ZF;
        } else if (op == 5) { // ge
            return (!SF || OF) || ZF;
        } else if (op == 6) { // g
            return (!SF || OF);
        } else {
            return false;
        }
    }
}
