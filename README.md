# 🎓 하렴 - 실시간 화상 과외 서비스

## 프로젝트 진행 기간
2024.01.08(월) ~ 2024.02.16(금) (총 40일)

## 배경
갈수록 수도권에 인구가 집중되는 지금, 지방에서도 명문대 선생님한테 과외를 받을 방법은 없을까요? 내가 받은 과외를 영상으로 다시 확인할 수 없을까요?

하렴은 이러한 요구에 맞춰 만든 1대1 온라인 화상과외 플랫폼입니다. 하렴을 통해 언제 어디서나 선생님으로부터 과외를 받고, 선생님이 내준 숙제를 풀고, 복습 영상을 다시 보세요!

## 주요 기능
### 1. 실시간 1:1 화상 과외 
* 학생과 선생님이 화상 과외방에 접속하여 과외를 동시에 진행할 수 있습니다.
* 드로잉 툴을 활용하여 학생과 선생님 모두 필기를 할 수 있습니다.
* 화이트보드를 기본으로, 학습자료 및 숙제를 불러와서 그 위에 드로잉을 진행할 수 있습니다.
* 선생님이 수업 시작과 종료 버튼을 눌러, 수업 진행 시간을 기록하고 과외 영상을 녹화할 수 있습니다.
* 수업 진행 중 타임스탬프를 눌러, 복습 시 필요한 부분을 미리 지정해서 기록해놓을 수 있습니다.
![과외화면](https://i.esdrop.com/d/f/vzTSBgB7JS/8PeaLBC1dq.png)

### 2. 선생님 찾기
* 선생님 찾기 페이지에서 과외를 구하고 있는 선생님 카드 목록을 확인할 수 있습니다.
* 선생님 목록은 과목, 학교, 경력, 성별, 수업료 등의 항목으로 필터링할 수 있습니다.
* 원하는 선생님 카드를 클릭하면 선생님 상세 정보를 볼 수 있고, 1대1 채팅방을 개설해 과외 문의를 할 수 있습니다.
![선생님 찾기](https://i.esdrop.com/d/f/vzTSBgB7JS/UwsDgvyMO9.png)
![선생님 찾기 상세](https://i.esdrop.com/d/f/vzTSBgB7JS/FAZUMcrguO.png)
### 3. 학생 선생님 간 1대1 채팅
* 1대1 채팅방에서는 기본 채팅 기능과 과외 신청 기능이 있습니다.
* 학생은 선생님께 과외 신청을 할 수 있고, 이 때 과외 과목과 수업료를 제안할 수 있습니다.
* 선생님은 해당 과외 신청이 들어온 것을 보고, 과외 신청을 수락 및 거절할 수 있습니다.
* 선생님이 과외를 수락할 시, 과외가 성사되고, 채팅방은 계속 유지됩니다.
![채팅 과외 요청](https://i.esdrop.com/d/f/vzTSBgB7JS/2rSmDUGsir.png)
![실시간 채팅](https://i.esdrop.com/d/f/vzTSBgB7JS/Li9XUX5nq1.gif)
### 3. 과외 일정 관리
* 선생님은 과외 일정을 등록하여 수업을 진행할 수 있습니다.
* 사용자는 마이홈의 달력을 통해 선생님이 등록한 과외 일정을 확인하고, 시간에 맞춰 과외방에 접속할 수 있습니다.
![과외 일정 조회](https://i.esdrop.com/d/f/vzTSBgB7JS/0VipqXNZn8.png)
### 4. 학습자료 관리
* 선생님은 과외 시 활용할 pdf 수업자료를 업로드할 수 있습니다.
* 선생님은 학습자료를 열람할 수 있는 과외학생을 등록할 수 있습니다.
* 선생님과 등록된 과외학생은 업로드된 수업자료를 열람할 수 있습니다.
![학습자료등록](https://i.esdrop.com/d/f/vzTSBgB7JS/QPp2oMHXFw.png)
![학습자료열람](https://i.esdrop.com/d/f/vzTSBgB7JS/9FJ7bGkvhM.png)
### 5. 숙제 관리
* 선생님은 학습자료에서 페이지를 지정해 학생에게 숙제를 내줄 수 있습니다.
* 학생은 드로잉 툴을 활용해 숙제를 풀고, 제출할 수 있습니다.
* 사용자는 숙제의 진행상태를 확인하고 다시 열람할 수 있습니다.
![숙제 드로잉 및 제출](https://i.esdrop.com/d/f/vzTSBgB7JS/ddHLMbvLdV.gif)
![숙제 상태 확인](https://i.esdrop.com/d/f/vzTSBgB7JS/vSKUnlsSLY.png)

### 6. 과외 영상 다시보기
* 학생은 수업 중 녹화된 영상을 활용해 복습할 수 있습니다.
* 수업 중 등록했던 타임스탬프를 눌러 원하는 시간대의 영상을 다시 볼 수 있습니다.
![복습화면](https://i.esdrop.com/d/f/vzTSBgB7JS/Oq5rTScmqa.png)


## 주요 기술
### Frontend
* Visual Studio Code IDE
* TypeScript 5.3.3
* Next.js 14.0.4
* React
* React Query
* Styled Components
* Recoil
* Jest
* Node.js
* Sock.js-Client
* Stomp.js
### Backend
* IntelliJ IDE
* Java JDK 11.0.21
* Springboot 2.7.18
* Spring Data JPA
* QueryDSL
* WebSocket
* Stomp
* Spring Security
* JWT
* Oauth 2.0
* Redis 
* MariaDB
* MongoDB
### Infra
* AWS EC2
* Nginx
* Jenkins
* Docker
* AWS S3
* AWS CloudFront
## 기술 특이점
### Frontend
* 화상과외 드로잉
    * HTML canvas를 활용해 과외, 숙제 드로잉
    * 실시간 과외 시 선생님과 학생의 드로잉 레이어를 분리하여, WebRTC를 통해 드로잉 데이터 교환이 가능하게 구현
    * 분리된 레이어가 겹쳐져 있어 사용자는 같은 화면에 드로잉된다 느껴지도록 구현
* 서버 사이드 렌더링
    * 한 화면의 렌더링에 많은 api 호출이 필요
    * 빠른 렌더링을 통해 UX를 향상시키기 위해 SSR 방식을 지원하는 Next.js 프레임워크 사용
### Backend
* 회원 인증 / 인가
    * Spring Security와 JWT Token을 활용해 회원 인증 및 인가 기능을 구현했습니다.
    * Redis에 Refresh Token을 저장하여 Refresh Token의 탈취 문제를 해결했습니다.
* 채팅방 구현
    * DB에 채팅 메시지의 입력되는 작업이 계속 반복되기 때문에, NoSQL DB인 MongoDB를 활용해 메시지를 저장했습니다.
* 대용량 파일 업로드 및 접근
    * 학습자료, 숙제, 과외 영상을 대용량 스토리지 서비스인 AWS S3 버킷에 저장했습니다.
    * AWS CloudFront를 활용해 해당 자료에 빠른 접근이 가능하게 했습니다.
## 프로젝트 파일 구조
### Frontend
```
├─public
│  └─images
└─src
    ├─apis
    │  ├─chat
    │  ├─homework
    │  ├─matching
    │  ├─tutoring
    │  └─user
    ├─components
    │  ├─ChatRoomList
    │  ├─Chatting
    │  ├─ClassSchedule
    │  ├─commons
    │  ├─CreateNewClass
    │  ├─CreateNewHomework
    │  ├─DrawingTools
    │  ├─FilterOpenTeacherList
    │  ├─Header
    │  ├─Homework
    │  ├─icons
    │  ├─layouts
    │  ├─MatchingStage
    │  ├─MediaStreamDisplay
    │  ├─OpenTeacherList
    │  ├─PaintCanvas
    │  │  └─hooks
    │  ├─PdfViewer
    │  └─RegisterUserInfoForm
    ├─config
    ├─containers
    │  ├─ChatContainer
    │  ├─ClassContainer
    │  ├─FindTeacherContainer
    │  ├─HomeworkContainer
    │  ├─LoginContainer
    │  ├─RegisterUserInfoContainer
    │  ├─ReviewContainer
    │  ├─ScheduleContainer
    │  └─TextbookContainer
    ├─hocs
    ├─hooks
    ├─pages
    │  ├─class
    │  ├─homework
    │  ├─review
    │  └─textbook
    ├─queries
    ├─recoil
    │  └─atoms
    ├─theme
    └─utils
```
### Backend
```
├── dump
│   ├── mariadb
│   └── mongodb
├── gradle
│   └── wrapper
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── ioi
    │   │           └── haryeom
    │   │               ├── advice
    │   │               │   └── exception
    │   │               ├── auth
    │   │               │   ├── controller
    │   │               │   ├── dto
    │   │               │   ├── exception
    │   │               │   ├── filter
    │   │               │   ├── service
    │   │               │   └── type
    │   │               ├── aws
    │   │               │   └── exception
    │   │               ├── chat
    │   │               │   ├── controller
    │   │               │   ├── document
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── exception
    │   │               │   ├── listener
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── common
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── interceptor
    │   │               │   ├── repository
    │   │               │   └── util
    │   │               ├── config
    │   │               ├── homework
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── exception
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── matching
    │   │               │   ├── controller
    │   │               │   ├── document
    │   │               │   ├── dto
    │   │               │   ├── exception
    │   │               │   ├── listener
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── member
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   │   └── type
    │   │               │   ├── dto
    │   │               │   ├── exception
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── textbook
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── exception
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               ├── tutoring
    │   │               │   ├── controller
    │   │               │   ├── domain
    │   │               │   ├── dto
    │   │               │   ├── exception
    │   │               │   ├── repository
    │   │               │   └── service
    │   │               └── video
    │   │                   ├── controller
    │   │                   ├── domain
    │   │                   ├── dto
    │   │                   ├── exception
    │   │                   ├── repository
    │   │                   └── service
    │   └── resources
    │       └── db
    └── test
        └── java
            └── com
                └── ioi
                    └── haryeom
                        └── homework
                            └── service
```
## 서비스 아키텍처
![하렴 서비스 아키텍처](https://i.esdrop.com/d/f/vzTSBgB7JS/94oHlXRlYg.png)
## 협업 환경
* GitLab
    * Git-flow 브랜치 전략을 활용한 버전 관리
    * 각자 브랜치에서 작업 후 develop 브랜치로 merge
* Notion
    * 매일 아침 스크럼 회의 및 추가 회의 기록
    * 컨벤션 정리
    * 프로젝트 산출물 관리
* JIRA
    * 1주일 단위로 스프린트 진행
    * 업무마다 할당량을 정하여 스토리 포인트를 설정하고, 작업 경과에 따라 To-do -> In-Progress -> Done 순으로 전환
## 프로젝트 산출물
* [요구사항 정의서](https://accessible-hurricane-f87.notion.site/6077913b3b844a3c938d66294037ce91)
* [화면 정의서](https://www.figma.com/file/Pq6weyuxBfTKailEaKutkA/ioi?type=design&node-id=0-1&mode=design&t=yBdO04ZWC0qggsc0-0)
* [인터페이스 설계서](https://accessible-hurricane-f87.notion.site/ea71918cebd54d62b37206994d70db98)
* [API 명세서](https://accessible-hurricane-f87.notion.site/9999ab7a12b54c7e8a63cbd5e382e963?v=d2348ccc306446fa840b2c3751fb7ba7)
* [ERD 다이어그램](https://www.notion.so/f99c1f8e664c45e9a9af2f3a18de01cf?p=68d0107f82d54f29bae5be017d5fcf54&pm=s)

연결된 링크를 통해 자세한 내용을 확인해주세요
## 팀원 역할 분배
<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/Seongeuniii"><img src=""  alt=""/><br /><sub><b>팀장 : 김성은</b></sub></a><br /><sub>FE 총괄</sub></td>
      <td align="center"><a href="https://github.com/s-yeong"><img src="https://avatars.githubusercontent.com/u/102036033?v=4" alt=""/><br /><sub><b>BE 팀장 : 이상영</b></sub></a><br /><sub>채팅, QA, BE 총괄</sub></td>
      <td align="center"><a href="https://github.com/leetaggg"><img src="https://avatars.githubusercontent.com/u/100212241?v=4" alt=""/><br /><sub><b>BE 팀원 : 이태호</b></sub></a><br /><sub>회원 관리</sub></td>
    </tr>
    <tr>
    <td align="center"><a href="https://github.com/hajaeryul"><img src="https://avatars.githubusercontent.com/u/113097210?v=4" alt=""/><br /><sub><b>BE 팀원 : 하재률</b></sub></a><br /><sub>학습자료, 숙제 관리</sub></td>
      <td align="center"><a href="https://github.com/ComelyU"><img src="https://avatars.githubusercontent.com/u/31150286?v=4" alt=""/><br /><sub><b>BE 팀원: 허준혁</b></sub></a><br /><sub>인프라, 숙제 일정</sub></td>
      <td align="center"><a href="https://github.com/tykim97"><img src="https://avatars.githubusercontent.com/u/139600380?v=4" alt=""/><br /><sub><b>BE 팀원 : 김태윤</b></sub></a><br /><sub>영상</sub></td>
    </tr>
  </tbody>
</table>
<style>
    table img{
        max-height: 100px;
        max-width: 100px;
    }
</style>