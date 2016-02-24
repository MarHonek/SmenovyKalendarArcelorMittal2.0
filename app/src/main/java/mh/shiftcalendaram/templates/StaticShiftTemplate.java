package mh.shiftcalendaram.templates;

import java.util.ArrayList;

/**
 * Created by Martin on 03.02.2016.
 */
public class StaticShiftTemplate {


        String title;
        String firstShift;
        String secondShift;
        String thirdShift;
        String forthShift;
        int numberOfShifts;
        static ArrayList<StaticShiftTemplate> listOfShifts;

        private StaticShiftTemplate(String title, String firstShift, String secondShift) {
            this.title = title;
            this.firstShift = firstShift;
            this.secondShift = secondShift;
            numberOfShifts = 2;
        }

        private StaticShiftTemplate(String title, String firstShift, String secondShift, String thirdShift) {
            this.title = title;
            this.firstShift = firstShift;
            this.secondShift = secondShift;
            this.thirdShift = thirdShift;
            numberOfShifts = 3;
        }

        private StaticShiftTemplate(String title, String firstShift, String secondShift, String thirdShift, String forthShift) {
            this.title = title;
            this.firstShift = firstShift;
            this.secondShift = secondShift;
            this.thirdShift = thirdShift;
            this.forthShift = forthShift;
            numberOfShifts = 4;
        }

        public int getNumberOfShits() {
            return numberOfShifts;
        }

        public static ArrayList<StaticShiftTemplate> createList() {
            listOfShifts = new ArrayList<StaticShiftTemplate>();
            listOfShifts.add(new StaticShiftTemplate("0058-0088", "O;N;N;V;V;R;R;O", "N;V;V;R;R;O;O;N", "V;R;R;O;O;N;N;V", "R;O;O;N;N;V;V;R"));
            listOfShifts.add(new StaticShiftTemplate("0018-0048", "O;R;R;V;V;N;N;O", "R;V;V;N;N;O;O;R", "V;N;N;O;O;R;R;V", "N;O;O;R;R;V;V;N"));
            listOfShifts.add(new StaticShiftTemplate("0428-0458", "N;O;O;V;V;R;R;N", "O;V;V;R;R;N;N;O", "V;R;R;N;N;O;O;V", "R;N;N;O;O;V;V;R"));
            listOfShifts.add(new StaticShiftTemplate("0858-0888, 5858-5888, 0958-0988, 5958-5988", "V;V;R;N", "R;N;V;V", "V;R;N;V", "N;V;V;R"));
            listOfShifts.add(new StaticShiftTemplate("0918-0948, 5918-5948", "V;N;N;V;V;R;R;V", "R;V;V;N;N;V;V;R", "V;R;R;V;V;N;N;V", "N;V;V;R;R;V;V;N"));
            listOfShifts.add(new StaticShiftTemplate("0318-0338", "O;V;V;R;R;O", "V;R;R;O;O;V", "R;O;O;V;V;R"));
            listOfShifts.add(new StaticShiftTemplate("3318-3338", "V;O;O;O;O;O;O;V;V;R;R;R;R;R;R;V;V;N;N;N;N;N;V;V", "V;N;N;N;N;N;V;V;V;O;O;O;O;O;O;V;V;R;R;R;R;R;R;V;", "V;R;R;R;R;R;R;V;V;N;N;N;N;N;V;V;V;O;O;O;O;O;O;V"));
            listOfShifts.add(new StaticShiftTemplate("0218-0238", "R;V;V;R;R;R", "V;R;R;R;R;V", "R;R;R;V;V;R"));
            listOfShifts.add(new StaticShiftTemplate("0268-0288", "O;V;V;R;R;O", "V;R;R;O;O;V", "R;O;O;V;V;R"));
            listOfShifts.add(new StaticShiftTemplate("0818-0828", "V;V;V;R;R;R", "V;V;V;N;N;N"));
            listOfShifts.add(new StaticShiftTemplate("0618-0648", "V;V;V;N;N;N;V;V;V;R;R;R", "V;V;V;R;R;R;V;V;V;N;N;N", "N;N;N;V;V;V;R;R;R;V;V;V", "R;R;R;V;V;V;N;N;N;V;V;V"));
            listOfShifts.add(new StaticShiftTemplate("0838-0848", "R;V;V;R", "V;R;R;V"));
            listOfShifts.add(new StaticShiftTemplate("0368-0348", "R;V;V;O;O;R", "V;O;O;R;R;V", "O;R;R;V;V;O"));
            listOfShifts.add(new StaticShiftTemplate("7438-7458", "O;O;O;V;V;R;R;R;R;R;V;V;N;N;N;N;N;V;V;O;O", "N;N;N;V;V;O;O;O;O;O;V;V;R;R;R;R;R;V;V;N;N", "R;R;R;V;V;N;N;N;N;N;V;V;O;O;O;O;O;V;V;R;R"));
            listOfShifts.add(new StaticShiftTemplate("AMFM", "D;D;O;O;O;N;N;V;V;D;D;D;O;O;N;N;V;V;V;D;D;O;O;N;N;KV;V;V", "O;O;N;N;KV;V;V;D;D;O;O;O;N;N;V;V;D;D;D;O;O;N;N;V;V;V;D;D", "N;N;V;V;V;D;D;O;O;N;N;KV;V;V;D;D;O;O;O;N;N;V;V;D;D;D;O;O", "V;V;D;D;D;O;O;N;N;V;V;V;D;D;O;O;N;N;KV;V;V;D;D;O;O;O;N;N"));
            listOfShifts.add(new StaticShiftTemplate("0918-5948", "V;V;V;V;D;D;N;N", "V;V;R;R;N;N;V;V", "D;D;N;N;V;V;V;V", "N;N;V;V;V;V;D;D"));
            listOfShifts.add(new StaticShiftTemplate("Kontrola", "R;R;NO;NO; ; ; ; ", "NO;NO; ; ; ; ;R;R", " ; ;R;R;NO;NO; ; ", " ; ; ; ;R;R;NO;NO"));
            listOfShifts.add(new StaticShiftTemplate("Neznámá", " ; ;N;N;N; ; ;R;R; ; ; ;N;N; ; ;R;R;R; ; ;N;N; ; ; ;R;R", "N;N; ; ; ;R;R; ; ;N;N;N; ; ;R;R; ; ; ;N;N; ; ;R;R;R; ; ", " ; ;R;R;R; ; ;N;N; ; ; ; R;R; ; ;N;N;N; ; ;R;R; ; ; ;N;N", "R;R; ; ; ;N;N; ; ;R;R;R; ; ;N;N; ; ; ; R;R; ; ;N;N;N; ; "));

            return listOfShifts;

        }

        public String getTitle() {
            return title;
        }

        public String getShiftA() {
            return firstShift;
        }

        public String getShiftB() {
            return secondShift;
        }

        public String getShiftC() {
            return thirdShift;
        }

        public String getShiftD() {
            return forthShift;
        }


        public static ArrayList<String> getStringArray() {
            ArrayList<StaticShiftTemplate> pernament = StaticShiftTemplate.createList();
            ArrayList<String> getString = new ArrayList<String>();
            for (int i = 0; i < pernament.size(); i++) {
                getString.add(pernament.get(i).getTitle());
            }
            return getString;
        }

        public String getABCDShifts(String radio) {

            String shiftScheme = null;
            switch (radio) {
                case "A":
                    shiftScheme = getShiftA();
                    break;
                case "B":
                    shiftScheme = getShiftB();
                    break;
                case "C":
                    shiftScheme = getShiftC();
                    break;
                case "D":
                    shiftScheme = getShiftD();
                    break;
            }

            return shiftScheme;
        }
}
