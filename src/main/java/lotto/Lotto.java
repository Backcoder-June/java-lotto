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
                lotto.sortLottoNum(lotto);
                lottoNumList.add(lotto);
                System.out.println(lotto);
        }
        return lottoNumList;
    }

    public Lotto sortLottoNum(Lotto lotto) {
        Collections.sort(lotto.numbers);
        return lotto;
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

    public static String getBenefit(Map<Integer, Integer> matchingCountNum, String price) {
        double totalPrice = 0;
        for (Integer matchingCount:matchingCountNum.keySet()
             ) {
            if (matchingCountNum.get(matchingCount) != 0) {
                if (matchingCount == 3) {totalPrice += 5000 * matchingCountNum.get(matchingCount);}
                if (matchingCount == 4) {totalPrice += 50000 * matchingCountNum.get(matchingCount);}
                if (matchingCount == 5) {totalPrice += 1500000 * matchingCountNum.get(matchingCount);}
                if (matchingCount == 6) {totalPrice += 2000000000 * matchingCountNum.get(matchingCount);}
                if (matchingCount == 7) {totalPrice += 3000000 * matchingCountNum.get(matchingCount);}
            }
        }
        return new DecimalFormat("###,###.0").format((totalPrice/Integer.parseInt(price))*100);
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
}
