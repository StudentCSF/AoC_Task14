import java.util.*;

public class Main {

    public static void main(String[] args) {
        task14();
    }

    static final int N = 40;

    static void task14() {
        Scanner in = new Scanner(System.in);

        String str = in.nextLine();

        Map<Character, Map<Character, Long>> mi = new HashMap<>();
        for (int i = 0; i < str.length() - 1; i++) {
            char c = str.charAt(i);
            char nc = str.charAt(i + 1);
            if (!mi.containsKey(c)) {
                mi.put(c, new HashMap<>());
            }
            if (!mi.get(c).containsKey(nc)) {
                mi.get(c).put(nc, 0L);
            }
            mi.get(c).put(nc, mi.get(c).get(nc) + 1);
        }

        in.nextLine();

        Map<Character, Map<Character, Character>> mc = new HashMap<>();
        while (true) {
            String s = in.nextLine();
            if (s.length() < 4) break;

            String[] s2 = s.split(" -> ");
            char c1 = s2[0].charAt(0);
            char c2 = s2[0].charAt(1);
            char c3 = s2[1].charAt(0);

            if (!mc.containsKey(c1)) mc.put(c1, new HashMap<>());
            mc.get(c1).put(c2, c3);

            if (!mi.containsKey(c1)) {
                mi.put(c1, new HashMap<>());
            }
            if (!mi.get(c1).containsKey(c2)) {
                mi.get(c1).put(c2, 0L);
            }
        }

        Map<Character, Map<Character, Long>> buffer = new HashMap<>();
        for (Map.Entry<Character, Map<Character, Long>> kv : mi.entrySet()) {
            buffer.put(kv.getKey(), new HashMap<>());
            for (Map.Entry<Character, Long> kv2 : kv.getValue().entrySet()) {
                buffer.get(kv.getKey()).put(kv2.getKey(), 0L);
            }
        }

        for (int k = 1; k <= N; k++) {
            for (Map.Entry<Character, Map<Character, Long>> kv : mi.entrySet()) {
                for (Map.Entry<Character, Long> kv2 : kv.getValue().entrySet()) {
                    long v = kv2.setValue(0L);
                    char mid = mc.get(kv.getKey()).get(kv2.getKey());
                    buffer.get(kv.getKey()).put(mid, buffer.get(kv.getKey()).get(mid) + v);
                    buffer.get(mid).put(kv2.getKey(), buffer.get(mid).get(kv2.getKey()) + v);
                }
            }

            for (Map.Entry<Character, Map<Character, Long>> kv : mi.entrySet()) {
                for (Map.Entry<Character, Long> kv2 : kv.getValue().entrySet()) {
                    long v = buffer.get(kv.getKey()).put(kv2.getKey(), 0L);
                    kv2.setValue(kv2.getValue() + v);
                }
            }
        }

        Map<Character, Long> freqs = new HashMap<>();
        for (Map.Entry<Character, Map<Character, Long>> kv : mi.entrySet()) {
            freqs.put(kv.getKey(), 0L);
        }
        freqs.put(str.charAt(0), 1L);
        for (Map.Entry<Character, Map<Character, Long>> kv : mi.entrySet()) {
            for (Map.Entry<Character, Long> kv2 : kv.getValue().entrySet()) {
                freqs.put(kv2.getKey(), freqs.get(kv2.getKey()) + kv2.getValue());
            }
        }

        long max = 0L;
        long min = Long.MAX_VALUE;
        for (long v : freqs.values()) {
            max = Math.max(max, v);
            min = Math.min(min, v);
        }

        long res = max - min;
        System.out.println(res);
    }
}