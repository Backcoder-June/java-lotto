package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.text.DecimalFormat;
import java.util.*;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
            validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 총 6개를 입력해야 합니다.");
        }
        if (numbers.size() == 6 && convertListToSet(numbers).size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
        for (Integer eachNum:numbers
             ) {
            if (eachNum < 1 || 45 < eachNum) {
                throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
            }
        }
    }
    // TODO: 추가 기능 구현
    @Override
    public String toString() {
        return "" + numbers ;
    }

    public static List<Lotto> getLottoNum(Integer lottoCount){
        List<Lotto> lottoNumList = new ArrayList<>();
        for (int i = 0; i < lottoCount; i++) {
            Lotto lotto = new Lotto(Randoms.pickUniqueNumbersInRange(1, 45, 6));
            Set sortedLotto = lotto.sortLottoNum(lotto);
            lottoNumList.add(lotto);
            System.out.println(sortedLotto);
        }
        return lottoNumList;
    }

    public Set sortLottoNum(Lotto lotto) {
        Set sortingSet = new TreeSet();
        for (Integer eachNum:lotto.numbers
             ) {
            sortingSet.add(eachNum);
        }
        return sortingSet;
    }

    public static Set<Integer> convertStringToIntegerSet(String winningNumbers){
        String[] split = winningNumbers.split(",");
        Set<Integer> IntegerSet = new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            IntegerSet.add(Integer.parseInt(split[i]));
        }
        return IntegerSet;
    }

    public static List<Set<Integer>> convertLottoToIntegerSet(List<Lotto> lottoList) {
        List<Set<Integer>> LottoNumSetList = new ArrayList<>();

        for (int i = 0; i < lottoList.size(); i++) {
            Set<Integer> LottoNumSet = new HashSet<>();
            List<Integer> lottoNums = lottoList.get(i).numbers;
            for (Integer lottoNum:lottoNums
                 ) {
                LottoNumSet.add(lottoNum);
            }
            LottoNumSetList.add(LottoNumSet);
        }
        return LottoNumSetList;
    }

    public static List<Integer> convertSetToList(Set<Integer> lottoList) {
        List<Integer> tempList = new ArrayList<>();
        for (Integer lottoNum:lottoList
             ) {
            tempList.add(lottoNum);
        }
        return tempList;
    }

    public static Set<Integer> convertListToSet(List<Integer> lottoList) {
        Set<Integer> tempSet = new HashSet<>();
        for (Integer lottoNum:lottoList
        ) {
            tempSet.add(lottoNum);
        }
        return tempSet;
    }

    public static List<Integer> getMatchingCount(List<Set<Integer>> lottoList, Set<Integer> winningNumbers, int bonusNum) {
        List<Integer> matchingCountList = new ArrayList<>();
        for (int i = 0; i < lottoList.size(); i++) {
            List<Integer> tempList = convertSetToList(lottoList.get(i));
            Set<Integer> LottoNumSet = lottoList.get(i);
            LottoNumSet.retainAll(winningNumbers);
            if (LottoNumSet.size() == 5 && tempList.contains(bonusNum)) {
                    matchingCountList.add(7);
                    continue;
            }
            matchingCountList.add(LottoNumSet.size());
        }
        return matchingCountList;
    }

    public static Map<Integer,Integer> getMatchingCountNumber(List<Integer> matchingCountList) {
        Map<Integer, Integer> matchingCount_Number = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            matchingCount_Number.put(i + 3, 0);
        }
        matchingCount_Number.put(7, 0);
        for (int j = 0; j < matchingCountList.size(); j++) {
            for (Integer keySet:matchingCount_Number.keySet()) {
                if (matchingCountList.get(j)>=3 && matchingCountList.get(j) == keySet) {
                    matchingCount_Number.put(keySet, matchingCount_Number.get(keySet) + 1);
                }
            }
        }
        return matchingCount_Number;
    }

    public enum matching {
        THREE(3, 5000),
        FOUR(4, 50000),
        FIVE(5, 1500000),
        SIX(6, 2000000000),
        BONUS(7, 3000000);
        private final int count, value;
        private matching(int count, int value) {
            this.count = count;
            this.value = value;
        }
        public int getCount() {
            return this.count;
        }
        public int getValue() {
            return this.value;
        }
    }


    public static String getBenefit(Map<Integer, Integer> matchingCountNum, String price) {
        double totalPrice = 0;
        for (Integer matchingCount:matchingCountNum.keySet()
        ) {
            if (matchingCountNum.get(matchingCount) != 0) {
                if (matching.THREE.getCount() == matchingCount) {totalPrice += matching.THREE.getValue() * matchingCountNum.get(matchingCount);}
                if (matching.FOUR.getCount() == matchingCount) {totalPrice += matching.FOUR.getValue() * matchingCountNum.get(matchingCount);}
                if (matching.FIVE.getCount() == matchingCount) {totalPrice += matching.FIVE.getValue() * matchingCountNum.get(matchingCount);}
                if (matching.SIX.getCount() == matchingCount) {totalPrice += matching.SIX.getValue() * matchingCountNum.get(matchingCount);}
                if (matching.BONUS.getCount() == matchingCount) {totalPrice += matching.BONUS.getValue() * matchingCountNum.get(matchingCount);}
            }
        }
        return new DecimalFormat("###,##0.0").format((totalPrice/Integer.parseInt(price))*100);
    }




    public static StringBuilder convertMatchingInfoToMessage(Map<Integer, Integer> matchingCountNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("3개 일치 (5,000원) - ");
        sb.append(matchingCountNumber.get(3) + "개\n");
        sb.append("4개 일치 (50,000원) - ");
        sb.append(matchingCountNumber.get(4) + "개\n");
        sb.append("5개 일치 (1,500,000원) - ");
        sb.append(matchingCountNumber.get(5) + "개\n");
        sb.append("5개 일치, 보너스 볼 일치 (30,000,000원) - ");
        sb.append(matchingCountNumber.get(7) + "개\n");
        sb.append("6개 일치 (2,000,000,000원) - ");
        sb.append(matchingCountNumber.get(6) + "개");
        return sb;
    }

    public static void priceValidator(String price) {
        if (!price.matches("[\\d]{1,8}")) {
            throw new IllegalArgumentException("[ERROR] 1억 이하의 숫자만 입력 가능합니다. (1000원 단위)");
        }
        if (Integer.parseInt(price) % 1000 != 0 || price.equals("0")) {
            throw new IllegalArgumentException("[ERROR] 구입은 1000원 단위로만 가능합니다.");
        }
    }

    public static void winningNumFormatValidator(String winningNum) {
        if (!winningNum.matches("[0-9]{1,2},[0-9]{1,2},[0-9]{1,2},[0-9]{1,2},[0-9]{1,2},[0-9]{1,2}")) {
            throw new IllegalArgumentException("[ERROR] 당첨번호는 숫자,숫자,숫자... 형식으로 6개를 입력해주세요. ex)1,2,3,4,5,6");
        }
    }


    public static void winningNumValidator(Set<Integer> winningNumSet) {
        if (winningNumSet.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 당첨번호는 중복될 수 없습니다.");
        }
        for (Integer winnigNum:winningNumSet
             ) {
            if (winnigNum < 0 || 45 < winnigNum) {
                throw new IllegalArgumentException("[ERROR] 당첨번호는 1 ~ 45 사이의 숫자여야 합니다.");
            }
        }
    }

    public static void bonusNumValidator(String bonusNum, Set<Integer> winningNumSet) {
        if (!bonusNum.matches("[0-9]{1,2}")) {
            throw new IllegalArgumentException("[ERROR] 한 개의 숫자만 입력해 주세요.(1-45)");
        }
        if (Integer.parseInt(bonusNum) < 0 || 45 < Integer.parseInt(bonusNum)) {
            throw new IllegalArgumentException("[ERROR] 1~45 사이의 숫자만 가능합니다.");
        }
        if (winningNumSet.contains(Integer.parseInt(bonusNum))) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨번호와 중복될 수 없습니다.");
        }
    }

}
