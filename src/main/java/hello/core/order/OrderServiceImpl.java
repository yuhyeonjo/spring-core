package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    // 할인 정책을 변경하려면 클라이언트인 OrderServiceImpl 코드 고쳐야 함 (새로운 할인 정책 적용의 문제점)
    // DIP 위반 ( DiscountPolicy 가 아닌 구체 클래스에 의존하고 있음)
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    // RateDiscountPolicy 로 바꾸는 순간 OCP 위반
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 회원정보 조회 후
        Member member = memberRepository.findById(memberId);
        // 할인정책을 넘김
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
