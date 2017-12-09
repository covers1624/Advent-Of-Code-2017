

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by covers1624 on 8/12/2017.
 */
public class Day8 {

    public static void main(String[] args) throws Throwable {
        Map<String, Integer> registers = new HashMap<>();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(Day8.class.getResourceAsStream("day8.txt")));
        int whileMax = 0;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            int checkReg = registers.getOrDefault(parts[4], 0);
            int checkVal = Integer.parseInt(parts[6]);
            String compOp = parts[5];
            boolean pass;
            switch(compOp) {
                case ">":
                    pass = checkReg > checkVal;
                    break;
                case "<":
                    pass = checkReg < checkVal;
                    break;
                case ">=":
                    pass = checkReg >= checkVal;
                    break;
                case "<=":
                    pass = checkReg <= checkVal;
                    break;
                case "==":
                    pass = checkReg == checkVal;
                    break;
                case "!=":
                    pass = checkReg != checkVal;
                    break;
                default:
                    throw new IllegalStateException("Invalid check op: " + compOp);
            }
            if (pass) {
                String op = parts[1];
                String reg = parts[0];
                int val = Integer.parseInt(parts[2]);
                switch(op) {
                    case "inc" :
                        registers.put(reg, registers.getOrDefault(reg, 0) + val);
                        break;
                    case "dec" :
                        registers.put(reg, registers.getOrDefault(reg, 0) - val);
                        break;
                    default:
                        throw new IllegalStateException("Invalid op: " + op);
                }
                whileMax = Math.max(whileMax, registers.get(reg));
            }
        }
        int max = 0;
        for (Entry<String, Integer> entry : registers.entrySet()) {
            max = Math.max(max, entry.getValue());
            System.out.println(entry.getKey() + " >> " + entry.getValue());
        }
        System.out.println(max);
        System.out.println(whileMax);
    }

}
