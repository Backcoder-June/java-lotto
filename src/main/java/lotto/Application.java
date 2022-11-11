package lotto;

import camp.nextstep.edu.missionutils.Console;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        try {
            System.out.println("구입할 금액을 입력해주세요.");
            String price = Console.readLine();
            Lotto.priceValidator(price);
            Integer lottoCount = Integer.parseInt(price) / 1000;

            System.out.println("\n" + lottoCount + "개를 구매했습니다.");

            List<Lotto> lottoNumList = Lotto.getLottoNum(lottoCount);
            List<Set<Integer>> lottoNumSetList = Lotto.convertLottoToIntegerSet(lottoNumList);

            System.out.println("\n당첨 번호를 입력해 주세요.");
            String winningNumbers = Console.readLine();
            Lotto.winningNumFormatValidator(winningNumbers);

            Set<Integer> winningNumSet = Lotto.convertStringToIntegerSet(winningNumbers);
            Lotto.winningNumValidator(winningNumSet);

            System.out.println("\n보너스 번호를 입력해 주세요.");
            String bonusNumber = Console.readLine();
            Lotto.bonusNumValidator(bonusNumber, winningNumSet);

            List<Integer> matchingCount = Lotto.getMatchingCount(lottoNumSetList, winningNumSet, Integer.parseInt(bonusNumber));

            System.out.println("\n당첨 통계\n---");

            Map<Integer, Integer> matchingCountNumber = Lotto.getMatchingCountNumber(matchingCount);
            StringBuilder resultMessage = Lotto.convertMatchingInfoToMessage(matchingCountNumber);

            System.out.println(resultMessage);
            String benefit = Lotto.getBenefit(matchingCountNumber, price);
            System.out.println("총 수익률은 " + benefit + "%입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
