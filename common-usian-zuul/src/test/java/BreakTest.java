public class BreakTest {
    public static void main(String[] args) {
        //结束多个循环
        ok :
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 1999; j++) {
                for (int k = 0; k < 10; k++) {
                    if (i == 50 && j == 100) {
                        System.out.println(j+k+i);
                        break ok;
                    }
                }
            }
        }
    }
}
