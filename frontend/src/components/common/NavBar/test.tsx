<div class="sr_wheel _rotate_circle_wrap">
        <!--[D] .sr_list, .sr_item 인라인 스타일은 마크업 확인용입니다. 개발 시에는 제거하고 적용해주세요. -->
        <div class="sr_tool_wrap">
            <!--[D] 아이템 위치 및 인터랙션 관련 인라인 스타일
                - .sr_tool_list, .sr_tool_item 인라인 스타일은 마크업 확인용입니다. 개발 시, 제거하고 적용해주세요.
                - (참고) css에 포함된 transform 스타일 코드 계산식
                    - .sr_tool_list style="transform: rotate(회전각도);"
                    - .sr_tool_item style="rotate((n-1)*(아이템 사이 각도)) translate(중앙기준 첫번째 아이템의 위치값) rotate(-((n-1)*(아이템 사이 각도)+(회전각도))"
                    - 회전각도 초기값: -45deg
                    - 현재 중앙기준 첫번째 아이템의 위치값: 0, -112px (화면너비 w414 이상 해상도에서는 기본 Y값에 -11px인 -123px를 적용해주세요.)
                    - 현재 아이템 사이 각도: 45deg (8개 기준)
            -->
            <ul class="sr_tool_list _rotate_icon_wrap" style="transform: rotate(270deg);">
                <!--[D] 아이콘 타입별 클래스
                    - 음성: .sr_voice
                    - 내주변: .sr_around
                    - 검색: .sr_search
                    - 음악: .sr_music
                    - 렌즈: .sr_lens
                    - QR 바코드: .sr_qr
                    - 문자인식: .sr_character
                    - 스마트주문: .sr_smartorder
                    - 쇼핑렌즈: .sr_shopping (3월 이후 스펙)
                -->
                <li class="sr_tool_item sr_voice _rotate_icon" data-degree="0" style="left: 280px; top: 167.5px; transform: rotate(-270deg);">
                    <a href="#" class="sr_tool_a" data-nclick-code="grd.voice" data-trigger-launchapp="" data-ios-scheme="naversearchapp" data-ios-install="393499958" data-ios-universal-fullurl="https://naverapp.m.naver.com/?urlScheme=naversearchapp%3A%2F%2Fsearch%3Fqmenu%3Dvoicerecg%26version%3D28" data-android-scheme="naversearchapp" data-ios-query="search%3Fqmenu%3Dvoicerecg%26version%3D28" data-android-query="search?qmenu=voicerecg&amp;version=28" data-android-package="com.nhn.android.search"><span class="sr_name">음성</span></a>
                </li>
                <li class="sr_tool_item sr_around _rotate_icon" data-degree="45" style="left: 247.05px; top: 247.05px; transform: rotate(-270deg);">
                    <a href="https://s.search.naver.com/p/around/search.naver?" class="sr_tool_a" data-nclick-code="grd.around"><span class="sr_name">내주변</span></a>
                </li>
                <li class="sr_tool_item sr_search _rotate_icon" data-degree="90" style="left: 167.5px; top: 280px; transform: rotate(-270deg);">
                    <a href="#" class="sr_tool_a" data-trigger-search="" data-nclick-code="grd.search" onclick="return false"><span class="sr_name">검색</span></a>
                </li>
                <li class="sr_tool_item sr_papago _rotate_icon" data-degree="135" style="left: 87.9505px; top: 247.05px; transform: rotate(-270deg);">
                    <a href="https://papago.naver.com/" class="sr_tool_a"><span class="sr_name">파파고번역</span></a>
                </li>
                <li class="sr_tool_item sr_shopping _rotate_icon" data-degree="180" style="left: 55px; top: 167.5px; transform: rotate(-270deg);">
                    <a href="#" class="sr_tool_a" data-nclick-code="grd.order" data-trigger-launchapp="" data-ios-install="393499958" data-ios-universal-fullurl="https://naverapp.m.naver.com/?urlScheme=naversearchapp%3A%2F%2Fsearch%3Fqmenu%3Dsearchbyimage%26mode%3Dshoppinglens%26version%3D28" data-ios-scheme="naversearchapp" data-android-scheme="naversearchapp" data-ios-query="search%3Fqmenu%3Dsearchbyimage%26mode%3Dqrpay%26version%3D28" data-android-query="search?qmenu=searchbyimage&amp;mode=qrpay&amp;version=28" data-android-package="com.nhn.android.search"><span class="sr_name">쇼핑렌즈</span></a>
                </li>
                <li class="sr_tool_item sr_qr _rotate_icon" data-degree="225" style="left: 87.9505px; top: 87.9505px; transform: rotate(-270deg);">
                    <a href="#" class="sr_tool_a" data-nclick-code="grd.qcode" data-trigger-launchapp="" data-ios-install="393499958" data-ios-universal-fullurl="https://naverapp.m.naver.com/?urlScheme=naversearchapp%3A%2F%2Fsearch%3Fqmenu%3Dsearchbyimage%26mode%3Dcode%26version%3D28" data-ios-scheme="naversearchapp" data-android-scheme="naversearchapp" data-ios-query="search%3Fqmenu%3Dsearchbyimage%26mode%3Dcode%26version%3D28" data-android-query="search?qmenu=searchbyimage&amp;mode=code&amp;version=28" data-android-package="com.nhn.android.search"><span class="sr_name">QR바코드</span></a>
                </li>
                <li class="sr_tool_item sr_lens _rotate_icon" data-degree="270" style="left: 167.5px; top: 55px; transform: rotate(-270deg);">
                    <a href="#" class="sr_tool_a" data-ios-scheme="naversearchapp" data-ios-install="393499958" data-ios-universal-fullurl="https://naverapp.m.naver.com/?urlScheme=naversearchapp%3A%2F%2Fsearch%3Fqmenu%3Dsearchbyimage%26mode%3Dsmartlens%26version%3D28" data-android-scheme="naversearchapp" data-ios-query="search%3Fqmenu%3Dsearchbyimage%26mode%3Dsmartlens%26version%3D28" data-android-query="search?qmenu=searchbyimage&amp;mode=smartlens&amp;version=28" data-android-package="com.nhn.android.search" data-trigger-launchapp="" data-nclick-code="grd.smartlens"><span class="sr_name">렌즈</span></a>
                </li>

                <li class="sr_tool_item sr_music _rotate_icon" data-degree="315" style="left: 247.05px; top: 87.9505px; transform: rotate(-270deg);">
                    <a href="#" class="sr_tool_a" data-trigger-launchapp="" data-ios-scheme="naversearchapp" data-android-scheme="naversearchapp" data-ios-install="393499958" data-ios-universal-fullurl="https://naverapp.m.naver.com/?urlScheme=naversearchapp%3A%2F%2Fsearch%3Fqmenu%3Dmusic%26version%3D28" data-ios-query="search%3Fqmenu%3Dmusic%26version%3D28" data-android-query="search?qmenu=music&amp;version=28" data-android-package="com.nhn.android.search" data-nclick-code="grd.music"><span class="sr_name">음악</span></a>
                </li>
            </ul>
        </div>
        
    </div>


// 버튼 클릭 전의 스타일
    /* &::before {
      content: '';
      position: fixed;
      top: 50%;
      right: 0;
      left: 0;
      width: calc(var(--size) * 0.7);
      height: calc(var(--size) * 0.7);
      margin: 0 auto;
      background-color: #000000;
      transform: translateY(-50%);
      border-radius: 50%;
      box-shadow: inset 0 var(--shadow) #ffbeb8;
      transition:
        0.2s ease width,
        0.2s ease height;
    } */