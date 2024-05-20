package com.dummy;

import com.ssafy.stellar.constellation.dto.response.ConstellationDto;

public class DummyConstellation {
    public static ConstellationDto getConstellationAllDto1() {
        return new ConstellationDto("Aquarius", "가을", "물병자리설명",
                "물병자리", "2.16", "Aquarius.png",
                "그리스 신화에 의하면 물병자리는 독수리에게 납치당해 신들에게 술을 따르는 일을 하게 된 트로이의 왕자 가니메데로 알려져 있다. " +
                        "청춘의 여신 헤베가 신들을 위해 술을 따르는 일을 하고 있었는데, 어느 날 발목을 삐어 그 일을 할 수 없게 되자 제우스는 독수리로 변하여 이다(Ida)산에서" +
                        " 트로이(Troy)의 양떼를 돌보고 있던 미소년 가니메데를 납치해 갔다. 불멸의 컵에 물을 넘쳐흐르도록 가득 채우고 있는 물병자리의 주인공은 바로 미소년 가니메데이다."
                , "hwangdo13", "3.11");
    }

    public static ConstellationDto getConstellationAllDto2() {
        return new ConstellationDto("Aries", "가을", "양자리설명",
                "양자리", "4.19", "Aries.png",
                "그리스 신화에 따르면 테살리에 야타마스라고 불리는 왕이 살고 있었는데, " +
                        "그에게는 프릭수스와 헬레라는 두 남매가 있었다. 이들이 어렸을 때 " +
                        "어머니가 돌아가셔서 아이들은 계모의 시달림을 받으며 살았다. " +
                        "이것을 우연히 본 전령의 신 헤르메스는 남매를 불쌍히 여겨 황금 가죽을 가진 숫양을 " +
                        "가지고 내려와 아이들을 보다 행복한 곳으로 보내기 위해 양에 태웠다. 양의 등에 타고 하늘을 날던 중, " +
                        "어린 헬레는 그만 아시아와 유럽을 나누는 해협에 떨어지고 말았다. 훗날 사람들은 " +
                        "헬레의 가여운 운명을 기억하고자 이 해협을 헬레스폰트라 불렀다. 홀로 남은 프릭소스는 " +
                        "양을 타고 계속 날아가 흑해의 동쪽 연안에 자리 잡고 있는 콜키스에 안전하게 도착하였다. " +
                        "제우스는 이 양의 공로를 치하하여 하늘의 별자리로 만들었다.",
                "hwangdo13", "5.13");
    }
}
