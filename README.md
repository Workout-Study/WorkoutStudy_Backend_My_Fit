# WorkoutStudy_Backend_My_Fit_Service
## Intro

- 이 서비스는 Fit mate 프로젝트의 운동 기록, 운동 인증, 투표, 벌금 관리 역할을 담당하는 서비스 입니다.

## Period

Version 1.0.0 : 2024.03.08 ~ 2024.04.20 <br>
Version 2.0.0 : 2024.05.01 ~

## Architecture

## Used Tools

### Application

 <a href="https://github.com/JetBrains/kotlin"><img src="https://img.shields.io/badge/Kotlin-7F42FF?style=for-the-badge&logo=Kotlin&logoColor=white"/></a>
 <a href="https://openjdk.org/projects/jdk/17/"><img src="https://img.shields.io/badge/JDK 17-007396?style=for-the-badge&logo=Java&logoColor=white"/></a>
 <a href="https://spring.io/projects/spring-cloud"><img src="https://img.shields.io/badge/Spring Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/></a><br>
 <a href="https://gradle.org/"><img src="https://img.shields.io/badge/Gradle 8.6-02303A?style=for-the-badge&logo=Gradle&logoColor=white"></a>
 <a href="https://spring.io/projects/spring-boot/"><img src="https://img.shields.io/badge/Spring Boot 3.2.3-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"></a>
 <a href="https://spring.io/projects/spring-data-jpa"><img src="https://img.shields.io/badge/Spring Data Jpa-6DB33F?style=for-the-badge&logo=Spring Data Jpa&logoColor=white"></a>
 <a href="http://querydsl.com/"><img src="https://img.shields.io/badge/Query Dsl 5.0-6DB33F?style=for-the-badge&logo=Query DSL&logoColor=white"></a>

### Database

 <a href="https://www.mysql.com/"><img src="https://img.shields.io/badge/MySql 8-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"></a>
 <a href="https://redis.io/"><img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"></a>

### Infra

 <a href="https://kafka.apache.org/"><img src="https://img.shields.io/badge/Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white"></a>
 <a href="https://docs.docker.com/get-docker/"><img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"></a>
 <a href="https://docs.docker.com/compose/install/"><img src="https://img.shields.io/badge/Docker_Compose-2496ED?style=for-the-badge&logo=Docker&logoColor=white"></a>

### Test

 <a href="https://junit.org/junit5/"><img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"></a>
 <a href="https://site.mockito.org/"><img src="https://img.shields.io/badge/Mockito-25A162?style=for-the-badge&logo=Mockito&logoColor=white"></a>

### Docs

<a href="https://asciidoc.org/"><img src="https://img.shields.io/badge/asciidoc-6DB33F?style=for-the-badge&logo=asciidoctor&logoColor=white"></a>
 <a href="https://spring.io/projects/spring-restdocs"><img src="https://img.shields.io/badge/Spring Rest Docs-6DB33F?style=for-the-badge&logo=Read The Docs&logoColor=white"></a>

### Cloud

<a href="https://aws.amazon.com/ko/?nc2=h_lg"><img src="https://img.shields.io/badge/EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"></a>

### Communication

<a href="https://github.com/"><img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"></a>
 <a href="https://slack.com/intl/ko-kr/"><img src="https://img.shields.io/badge/slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"></a>
 <a href="https://www.notion.so/"><img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"></a>
 <a href="https://discord.com/"><img src="https://img.shields.io/badge/discord-5865F2?style=for-the-badge&logo=discord&logoColor=white"></a>

## Git Strategy

- Git flow

## API document

<!DOCTYPE html>
<html lang="en">
<body class="book toc2 toc-left">
<div id="header">
<h1>My-Fit Service API Document</h1>
<div class="details">
<span id="revnumber">Version 1.0.0</span>
</div>
<div id="toc" class="toc2">
<ul class="sectlevel0">
<li><a href="#common">개발 환경 정보</a>
<ul class="sectlevel1">
<li><a href="#my-fit-Service-API">1. fit-record</a>
<ul class="sectlevel2">
<li><a href="#_register_fit_record">Register Fit Record</a></li>
<li><a href="#_delete_fit_record">Delete Fit Record</a></li>
<li><a href="#_fit_record_filter_사용자의_기록_기간으로_filtering">Fit Record Filter ( 사용자의 기록 기간으로 filtering )</a></li>
<li><a href="#_fit_record_slice_filter_사용자의_기록_기간_slice로_조회">Fit Record Slice Filter ( 사용자의 기록 기간 Slice로 조회 )</a></li>
</ul>
</li>
<li><a href="#_2_fit_certification">2. fit-certification</a>
<ul class="sectlevel2">
<li><a href="#_register_fit_certification">Register Fit Certification</a></li>
<li><a href="#_delete_fit_certification">Delete Fit Certification</a></li>
<li><a href="#_proceeding_fit_certification_list_by_fit_group_id">Proceeding Fit Certification List By fit group id</a></li>
<li><a href="#_fit_certification_progress_list_by_fit_group_id">Fit Certification Progress List By fit group id</a></li>
</ul>
</li>
<li><a href="#_3_vote">3. vote</a>
<ul class="sectlevel2">
<li><a href="#_register_vote">Register vote</a></li>
<li><a href="#_delete_vote">Delete vote</a></li>
<li><a href="#_update_vote">Update vote</a></li>
</ul>
</li>
<li><a href="#_4_my_fit">4. my fit</a>
<ul class="sectlevel2">
<li><a href="#_fit_certification_progress_인증_진척도">Fit Certification Progress ( 인증 진척도 )</a></li>
<li><a href="#_need_vote_fit_certification_list_투표_필요_인증_목록">Need Vote Fit Certification List ( 투표 필요 인증 목록 )</a></li>
</ul>
</li>
<li><a href="#_5_fit_penalty">5. fit penalty</a>
<ul class="sectlevel2">
<li><a href="#_fit_penalty_filter_by_user_유저_기준으로_벌금_조회_slice">Fit Penalty Filter By User ( 유저 기준으로 벌금 조회 Slice )</a></li>
<li><a href="#_fit_penalty_filter_by_fit_group_fit_group_기준으로_벌금_조회_slice">Fit Penalty Filter By Fit group ( fit group 기준으로 벌금 조회 Slice )</a></li>
</ul>
</li>
<li><a href="#_6_fit_management">6. fit management</a>
<ul class="sectlevel2">
<li><a href="#_fit_management_fit_penalty_paid_by_fit_leader_패널티_금액_리더가_입금_완료_처리">fit management fit penalty paid by fit leader ( 패널티 금액 리더가 입금 완료 처리 )</a></li>
<li><a href="#_fit_management_fit_penalty_no_need_pay_by_fit_leader_패널티_금액_리더가_입금_불필요_처리">fit management fit penalty no need pay by fit leader ( 패널티 금액 리더가 입금 불필요 처리 )</a></li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>
<div id="content">
<h1 id="common" class="sect0"><a class="link" href="#common">개발 환경 정보</a></h1>
<div class="openblock partintro">
<div class="content">
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">환경</th>
<th class="tableblock halign-left valign-top">url</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">테스트</p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">http://127.0.0.1</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock">운영서버</p></td>
<td class="tableblock halign-left valign-top" ><p class="tableblock"><del>http://52.78.208.77</del></p></td>
</tr>
</tbody>
</table>
</div>
</div>
<div class="sect1">
<h2 id="my-fit-Service-API"><a class="link" href="#my-fit-Service-API">1. fit-record</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_register_fit_record"><a class="link" href="#_register_fit_record">Register Fit Record</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">POST /my-fit-service/records HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 210
Host: localhost:8080

{"requestUserId":"testUserId","recordStartDate":"2024-04-19T08:17:24.200708300Z","recordEndDate":"2024-04-20T12:04:04.200708300Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit record를 등록하는 User id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>recordStartDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기록 시작 시간</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>recordEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기록 종료 시간</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">멀티 미디어 end point list ( 주어진 index 순으로 return )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId","recordStartDate":"2024-04-19T08:17:24.200708300Z","recordEndDate":"2024-04-20T12:04:04.200708300Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/records' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId","recordStartDate":"2024-04-19T08:17:24.200708300Z","recordEndDate":"2024-04-20T12:04:04.200708300Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isRegisterSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 성공 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 시 fit record id ( 실패시 null )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isRegisterSuccess":true,"fitRecordId":1}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 201 Created
Content-Length: 42
Content-Type: application/json

{"isRegisterSuccess":true,"fitRecordId":1}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_delete_fit_record"><a class="link" href="#_delete_fit_record">Delete Fit Record</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 1. /my-fit-service/records/{fit-record-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-record-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제할 Fit record id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">DELETE /my-fit-service/records/124 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 30
Host: localhost:8080

{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit record를 삭제 요청한 User id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/records/124' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId"}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isDeleteSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isDeleteSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 24
Content-Type: application/json

{"isDeleteSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_fit_record_filter_사용자의_기록_기간으로_filtering"><a class="link" href="#_fit_record_filter_사용자의_기록_기간으로_filtering">Fit Record Filter ( 사용자의 기록 기간으로 filtering )</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/records/filters?userId=testUserId&amp;recordEndStartDate=2024-04-01T00:00:00Z&amp;recordEndEndDate=2024-04-30T23:59:59Z HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/records/filters?userId=testUserId&amp;recordEndStartDate=2024-04-01T00:00:00Z&amp;recordEndEndDate=2024-04-30T23:59:59Z' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].fitRecordId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].recordStartDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 기록 시작시간</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].recordEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 기록 종료시간</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 생성일</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 멀티 미디어 end point list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].registeredFitCertifications[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record가 등록돼있는 fit certification 목록 ( fit group 포함 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].registeredFitCertifications[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증이 등록돼있는 fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].registeredFitCertifications[].fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증이 등록돼있는 fit group의 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].registeredFitCertifications[].fitCertificationId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record가 인증으로 등록 돼 있는 fit certification id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].registeredFitCertifications[].certificationStatus</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 상태 (REQUESTED, CERTIFIED, REJECTED)</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].registeredFitCertifications[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 등록 일시</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordDetailResponseDtoList[].registeredFitCertifications[].voteEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 종료 일시</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"fitRecordDetailResponseDtoList":[{"fitRecordId":0,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]},{"fitRecordId":1,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]},{"fitRecordId":2,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]},{"fitRecordId":3,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 2748
Content-Type: application/json

{"fitRecordDetailResponseDtoList":[{"fitRecordId":0,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]},{"fitRecordId":1,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]},{"fitRecordId":2,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]},{"fitRecordId":3,"recordStartDate":"2024-04-19T08:17:27.064607600Z","recordEndDate":"2024-04-20T12:04:07.064607600Z","createdAt":"2024-04-19T08:17:27.066602500Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.064607600Z","voteEndDate":"2024-04-19T20:17:27.064607600Z"}]}]}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_fit_record_slice_filter_사용자의_기록_기간_slice로_조회"><a class="link" href="#_fit_record_slice_filter_사용자의_기록_기간_slice로_조회">Fit Record Slice Filter ( 사용자의 기록 기간 Slice로 조회 )</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/records/filters/slice?userId=testUserId&amp;recordEndStartDate=2024-04-01T00:00:00Z&amp;recordEndEndDate=2024-04-30T23:59:59Z&amp;pageNumber=0&amp;pageSize=5 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/records/filters/slice?userId=testUserId&amp;recordEndStartDate=2024-04-01T00:00:00Z&amp;recordEndEndDate=2024-04-30T23:59:59Z&amp;pageNumber=0&amp;pageSize=5' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitRecordId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].recordStartDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 기록 시작시간</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].recordEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 기록 종료시간</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 생성일</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].multiMediaEndPoints</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record의 멀티 미디어 end point list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].registeredFitCertifications[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record가 등록돼있는 fit certification 목록 ( fit group 포함 )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].registeredFitCertifications[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증이 등록돼있는 fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].registeredFitCertifications[].fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증이 등록돼있는 fit group의 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].registeredFitCertifications[].fitCertificationId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit record가 인증으로 등록 돼 있는 fit certification id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].registeredFitCertifications[].certificationStatus</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 상태 (REQUESTED, CERTIFIED, REJECTED)</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].registeredFitCertifications[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 등록 일시</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].registeredFitCertifications[].voteEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 종료 일시</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">pageable object</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.pageNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 페이지 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.pageSize</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 한 size</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort object</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort.empty</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 요청 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort.sorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.sort.unsorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">unsort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.offset</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">대상 시작 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.unpaged</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">unpaged</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageable.paged</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">paged</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>size</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">List 크기</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 페이지 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Object</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort object</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort.empty</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 요청 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort.sorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">sort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>sort.unsorted</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">unsort 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>numberOfElements</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">numberOfElements</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>first</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">처음인지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>last</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">마지막인지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>empty</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">비어있는지 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"content":[{"fitRecordId":0,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]},{"fitRecordId":1,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]},{"fitRecordId":2,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]},{"fitRecordId":3,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":" 뿬吏깆㎟","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":" 젙 씪 뿬吏깆㎟ 紐⑥엫","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]}],"pageable":{"pageNumber":0,"pageSize":5,"sort":{"empty":true,"sorted":false,"unsorted":true},"offset":0,"unpaged":false,"paged":true},"size":5,"number":0,"sort":{"empty":true,"sorted":false,"unsorted":true},"numberOfElements":4,"first":true,"last":false,"empty":false}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 2993
Content-Type: application/json

{"content":[{"fitRecordId":0,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]},{"fitRecordId":1,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]},{"fitRecordId":2,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]},{"fitRecordId":3,"recordStartDate":"2024-04-19T08:17:27.127439600Z","recordEndDate":"2024-04-20T12:04:07.127439600Z","createdAt":"2024-04-19T08:17:27.128436700Z","multiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"],"registeredFitCertifications":[{"fitGroupId":5,"fitGroupName":"헬짱짱","fitCertificationId":23,"certificationStatus":"REQUESTED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"},{"fitGroupId":767,"fitGroupName":"정일헬짱짱 모임","fitCertificationId":28,"certificationStatus":"CERTIFIED","createdAt":"2024-04-19T08:17:27.127439600Z","voteEndDate":"2024-04-19T20:17:27.127439600Z"}]}],"pageable":{"pageNumber":0,"pageSize":5,"sort":{"empty":true,"sorted":false,"unsorted":true},"offset":0,"unpaged":false,"paged":true},"size":5,"number":0,"sort":{"empty":true,"sorted":false,"unsorted":true},"numberOfElements":4,"first":true,"last":false,"empty":false}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_2_fit_certification"><a class="link" href="#_2_fit_certification">2. fit-certification</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_register_fit_certification"><a class="link" href="#_register_fit_certification">Register Fit Certification</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">POST /my-fit-service/certifications HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 71
Host: localhost:8080

{"requestUserId":"testUserId","fitRecordId":137,"fitGroupIds":[13,7,2]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit 인증을 등록하는 User id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitRecordId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증으로 등록할 record의 id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupIds</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증으로 등록할 group의 id list</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId","fitRecordId":137,"fitGroupIds":[13,7,2]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/certifications' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId","fitRecordId":137,"fitGroupIds":[13,7,2]}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isRegisterSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isRegisterSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 201 Created
Content-Length: 26
Content-Type: application/json

{"isRegisterSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_delete_fit_certification"><a class="link" href="#_delete_fit_certification">Delete Fit Certification</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 2. /my-fit-service/certifications/{fit-certification-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-certification-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제할 Fit certification id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">DELETE /my-fit-service/certifications/137 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 30
Host: localhost:8080

{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit certification을 삭제 요청한 User id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/certifications/137' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId"}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isDeleteSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isDeleteSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 24
Content-Type: application/json

{"isDeleteSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_proceeding_fit_certification_list_by_fit_group_id"><a class="link" href="#_proceeding_fit_certification_list_by_fit_group_id">Proceeding Fit Certification List By fit group id</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 3. /my-fit-service/certifications/filters/{fit-group-id}/{user-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">진행중인 Fit certification list를 조회할 group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>user-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Fit certification list를 조회하는 User id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/certifications/filters/12/testUserId HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs"></code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/certifications/filters/12/testUserId' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">진행중인 인증 list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].certificationId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 인증 id (fit certification id)</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].recordId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 인증의 record id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].certificationRequestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">운동 인증을 요청한 user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].isUserVoteDone</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회를 요청한 유저가 투표 했는지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].isUserAgree</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회를 요청한 유저가 찬성 했는지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].agreeCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">찬성 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].disagreeCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">반대 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].maxAgreeCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">최대 투표 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].thumbnailEndPoint</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기록 썸네일 사진</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].fitRecordStartDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기록 시작 일자</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].fitRecordEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">기록 종료 일자</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationDetails[].voteEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표 종료 일자</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"fitCertificationDetails":[{"certificationId":0,"recordId":0,"certificationRequestUserId":"testUserId0","isUserVoteDone":true,"isUserAgree":false,"agreeCount":4,"disagreeCount":1,"maxAgreeCount":13,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"},{"certificationId":1,"recordId":1,"certificationRequestUserId":"testUserId1","isUserVoteDone":true,"isUserAgree":false,"agreeCount":5,"disagreeCount":2,"maxAgreeCount":14,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"},{"certificationId":2,"recordId":2,"certificationRequestUserId":"testUserId2","isUserVoteDone":true,"isUserAgree":false,"agreeCount":6,"disagreeCount":3,"maxAgreeCount":15,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"},{"certificationId":3,"recordId":3,"certificationRequestUserId":"testUserId3","isUserVoteDone":true,"isUserAgree":false,"agreeCount":7,"disagreeCount":4,"maxAgreeCount":16,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 1633
Content-Type: application/json

{"fitCertificationDetails":[{"certificationId":0,"recordId":0,"certificationRequestUserId":"testUserId0","isUserVoteDone":true,"isUserAgree":false,"agreeCount":4,"disagreeCount":1,"maxAgreeCount":13,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"},{"certificationId":1,"recordId":1,"certificationRequestUserId":"testUserId1","isUserVoteDone":true,"isUserAgree":false,"agreeCount":5,"disagreeCount":2,"maxAgreeCount":14,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"},{"certificationId":2,"recordId":2,"certificationRequestUserId":"testUserId2","isUserVoteDone":true,"isUserAgree":false,"agreeCount":6,"disagreeCount":3,"maxAgreeCount":15,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"},{"certificationId":3,"recordId":3,"certificationRequestUserId":"testUserId3","isUserVoteDone":true,"isUserAgree":false,"agreeCount":7,"disagreeCount":4,"maxAgreeCount":16,"fitRecordStartDate":"2024-04-19T08:17:23.876574800Z","fitRecordEndDate":"2024-04-19T11:04:03.876574800Z","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","voteEndDate":"2024-04-30T22:04:03.876574800Z"}]}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_fit_certification_progress_list_by_fit_group_id"><a class="link" href="#_fit_certification_progress_list_by_fit_group_id">Fit Certification Progress List By fit group id</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 4. /my-fit-service/my-fits/certifications/progresses/{fit-group-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit mate들의 certification 진척도를 조회할 group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/my-fits/certifications/progresses/12 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs"></code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/my-fits/certifications/progresses/12' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회한 fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회한 fit group name</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>cycle</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 주기 ( 1: 일주일, 2: 한달, 3: 일년 ) - 현재는 일주일만 구현</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>frequency</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">주기별 인증 필요 횟수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit mate들의 인증 진행도</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].fitMateUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit mate의 user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].certificationCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">금주 인증 횟수</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"fitGroupId":12,"fitGroupName":" 뿬 뿬 뿬  뿬 뒪瑜   빐 슂","cycle":1,"frequency":7,"fitCertificationProgresses":[{"fitMateUserId":"testUserId1","certificationCount":3},{"fitMateUserId":"testUserId2","certificationCount":1}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 226
Content-Type: application/json

{"fitGroupId":12,"fitGroupName":"헬헬헬 헬스를 해요","cycle":1,"frequency":7,"fitCertificationProgresses":[{"fitMateUserId":"testUserId1","certificationCount":3},{"fitMateUserId":"testUserId2","certificationCount":1}]}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_3_vote"><a class="link" href="#_3_vote">3. vote</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_register_vote"><a class="link" href="#_register_vote">Register vote</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">POST /my-fit-service/votes HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 76
Host: localhost:8080

{"requestUserId":"testUserId","agree":true,"targetCategory":1,"targetId":13}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Vote를 등록하는 User id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>agree</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">찬반 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>targetCategory</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표를 등록할 target의 category ( target category - 1: fit certification )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>targetId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표를 등록할 target의 id ( ex. fit certification id )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId","agree":true,"targetCategory":1,"targetId":13}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/votes' -i -X POST \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId","agree":true,"targetCategory":1,"targetId":13}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isRegisterSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isRegisterSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 201 Created
Content-Length: 26
Content-Type: application/json

{"isRegisterSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_delete_vote"><a class="link" href="#_delete_vote">Delete vote</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">DELETE /my-fit-service/votes HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 63
Host: localhost:8080

{"requestUserId":"testUserId","targetCategory":1,"targetId":13}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Vote를 삭제요청한 User id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>targetCategory</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표를 삭제할 target의 category ( target category - 1: fit certification )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>targetId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표를 삭제할 target의 id ( ex. fit certification id )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId","targetCategory":1,"targetId":13}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/votes' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId","targetCategory":1,"targetId":13}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isDeleteSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">삭제 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isDeleteSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 24
Content-Type: application/json

{"isDeleteSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_update_vote"><a class="link" href="#_update_vote">Update vote</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">PUT /my-fit-service/votes HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 76
Host: localhost:8080

{"requestUserId":"testUserId","agree":true,"targetCategory":1,"targetId":13}</code></pre>
</div>
</div>
<div class="paragraph">
<p>request-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>requestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">Vote를 수정요청한 User id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>agree</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">찬반 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>targetCategory</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표를 수정할 target의 category ( target category - 1: fit certification )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>targetId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표를 수정할 target의 id ( ex. fit certification id )</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>request-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"requestUserId":"testUserId","agree":true,"targetCategory":1,"targetId":13}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/votes' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId","agree":true,"targetCategory":1,"targetId":13}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isUpdateSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isUpdateSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 24
Content-Type: application/json

{"isUpdateSuccess":true}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_4_my_fit"><a class="link" href="#_4_my_fit">4. my fit</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_fit_certification_progress_인증_진척도"><a class="link" href="#_fit_certification_progress_인증_진척도">Fit Certification Progress ( 인증 진척도 )</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/my-fits/certifications/progresses?requestUserId=testUserId HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/my-fits/certifications/progresses?requestUserId=testUserId' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">진척도 list</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].thumbnailEndPoint</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group의 썸네일 사진 end point</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].cycle</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증 주기 ( 1: 일주일, 2: 한달, 3: 일년 ) - 현재는 일주일만 구현</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].frequency</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">주기별 인증 필요 횟수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fitCertificationProgresses[].certificationCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">금주 인증 횟수</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"fitCertificationProgresses":[{"fitGroupId":1,"fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒1","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","cycle":1,"frequency":8,"certificationCount":5},{"fitGroupId":2,"fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒2","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","cycle":1,"frequency":9,"certificationCount":6},{"fitGroupId":3,"fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒3","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","cycle":1,"frequency":10,"certificationCount":7}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 678
Content-Type: application/json

{"fitCertificationProgresses":[{"fitGroupId":1,"fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디1","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","cycle":1,"frequency":8,"certificationCount":5},{"fitGroupId":2,"fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디2","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","cycle":1,"frequency":9,"certificationCount":6},{"fitGroupId":3,"fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디3","thumbnailEndPoint":"https://avatars.githubusercontent.com/u/105261146?v=4","cycle":1,"frequency":10,"certificationCount":7}]}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_need_vote_fit_certification_list_투표_필요_인증_목록"><a class="link" href="#_need_vote_fit_certification_list_투표_필요_인증_목록">Need Vote Fit Certification List ( 투표 필요 인증 목록 )</a></h3>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/my-fits/certifications/need-votes?requestUserId=testUserId HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/my-fits/certifications/need-votes?requestUserId=testUserId' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">유저가 투표해야할 인증 목록</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표해야할 인증이 있는 fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].fitGroupName</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표해야할 인증이 있는 fit group의 이름</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit group 내의 투표해야할 인증 목록</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].certificationId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표해야할 인증의 id ( fit certification id )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].recordId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표해야할 인증의 recordId ( fit record id )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].certificationRequestUserId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표해야할 인증을 요청한 유저 id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].agreeCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">찬성 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].disagreeCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">반대 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].maxAgreeCount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">최대 투표 수</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].voteEndDate</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">투표 종료 일자</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>needVoteCertificationFitGroupList[].needVoteCertificationList[].recordMultiMediaEndPoints[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">인증을 요청한 기록의 multi media end points</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"needVoteCertificationFitGroupList":[{"fitGroupId":1,"fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒","needVoteCertificationList":[{"certificationId":1,"recordId":2,"certificationRequestUserId":"testUserId","agreeCount":4,"disagreeCount":2,"maxAgreeCount":10,"voteEndDate":"2024-04-19T08:17:30.524382300Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]},{"certificationId":4,"recordId":2,"certificationRequestUserId":"testUserId","agreeCount":2,"disagreeCount":1,"maxAgreeCount":7,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}]},{"fitGroupId":2,"fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒","needVoteCertificationList":[{"certificationId":2,"recordId":3,"certificationRequestUserId":"testUserId","agreeCount":5,"disagreeCount":3,"maxAgreeCount":11,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]},{"certificationId":5,"recordId":3,"certificationRequestUserId":"testUserId","agreeCount":3,"disagreeCount":2,"maxAgreeCount":8,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}]},{"fitGroupId":3,"fitGroupName":" 뿬李쎈뱾    씪二쇱씪 뿉 7踰   슫 룞 빐 빞二   뒪 꽣 뵒","needVoteCertificationList":[{"certificationId":3,"recordId":4,"certificationRequestUserId":"testUserId","agreeCount":6,"disagreeCount":4,"maxAgreeCount":12,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]},{"certificationId":6,"recordId":4,"certificationRequestUserId":"testUserId","agreeCount":4,"disagreeCount":3,"maxAgreeCount":9,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}]}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 1971
Content-Type: application/json

{"needVoteCertificationFitGroupList":[{"fitGroupId":1,"fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","needVoteCertificationList":[{"certificationId":1,"recordId":2,"certificationRequestUserId":"testUserId","agreeCount":4,"disagreeCount":2,"maxAgreeCount":10,"voteEndDate":"2024-04-19T08:17:30.524382300Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]},{"certificationId":4,"recordId":2,"certificationRequestUserId":"testUserId","agreeCount":2,"disagreeCount":1,"maxAgreeCount":7,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}]},{"fitGroupId":2,"fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","needVoteCertificationList":[{"certificationId":2,"recordId":3,"certificationRequestUserId":"testUserId","agreeCount":5,"disagreeCount":3,"maxAgreeCount":11,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]},{"certificationId":5,"recordId":3,"certificationRequestUserId":"testUserId","agreeCount":3,"disagreeCount":2,"maxAgreeCount":8,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}]},{"fitGroupId":3,"fitGroupName":"헬창들은 일주일에 7번 운동해야죠 스터디","needVoteCertificationList":[{"certificationId":3,"recordId":4,"certificationRequestUserId":"testUserId","agreeCount":6,"disagreeCount":4,"maxAgreeCount":12,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]},{"certificationId":6,"recordId":4,"certificationRequestUserId":"testUserId","agreeCount":4,"disagreeCount":3,"maxAgreeCount":9,"voteEndDate":"2024-04-19T08:17:30.525379200Z","recordMultiMediaEndPoints":["https://avatars.githubusercontent.com/u/105261146?v=4"]}]}]}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_5_fit_penalty"><a class="link" href="#_5_fit_penalty">5. fit penalty</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_fit_penalty_filter_by_user_유저_기준으로_벌금_조회_slice"><a class="link" href="#_fit_penalty_filter_by_user_유저_기준으로_벌금_조회_slice">Fit Penalty Filter By User ( 유저 기준으로 벌금 조회 Slice )</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 5. /my-fit-service/penalties/filters/by-users/{user-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>user-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">penalty list를 조회할 user id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/penalties/filters/by-users/testUserId?startDate=2024-04-01T00:00:00Z&amp;endDate=2024-04-30T23:59:59Z&amp;onlyPaid=true&amp;onlyNotPaid=false&amp;pageNumber=0&amp;pageSize=5 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/penalties/filters/by-users/testUserId?startDate=2024-04-01T00:00:00Z&amp;endDate=2024-04-30T23:59:59Z&amp;onlyPaid=true&amp;onlyNotPaid=false&amp;pageNumber=0&amp;pageSize=5' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 페이지 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageSize</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 한 페이지 size</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>hasNext</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">다음 Slice가 있는지</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>totalAmount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">총 금액 ( 결과 리스트 안의 Amount의 sum이 아닌 조회 조건으로 Paging 되지 않은 끝 Row의 Amount sum )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 목록</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitPenaltyId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty가 발생한 fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].userId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty (벌금) 이 발부된 user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].amount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].paid</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 납부 했는지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].noNeedPay</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 납부할 필요 없는지 여부 ( ex-fit reader가 취소시켰을때 true )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty가 발생한 일시</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"pageNumber":0,"pageSize":5,"hasNext":false,"totalAmount":15000,"content":[{"fitPenaltyId":1,"fitGroupId":4,"userId":"testUserId1","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.995447900Z"},{"fitPenaltyId":2,"fitGroupId":5,"userId":"testUserId2","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.995447900Z"},{"fitPenaltyId":3,"fitGroupId":6,"userId":"testUserId3","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.995447900Z"}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 515
Content-Type: application/json

{"pageNumber":0,"pageSize":5,"hasNext":false,"totalAmount":15000,"content":[{"fitPenaltyId":1,"fitGroupId":4,"userId":"testUserId1","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.995447900Z"},{"fitPenaltyId":2,"fitGroupId":5,"userId":"testUserId2","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.995447900Z"},{"fitPenaltyId":3,"fitGroupId":6,"userId":"testUserId3","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.995447900Z"}]}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_fit_penalty_filter_by_fit_group_fit_group_기준으로_벌금_조회_slice"><a class="link" href="#_fit_penalty_filter_by_fit_group_fit_group_기준으로_벌금_조회_slice">Fit Penalty Filter By Fit group ( fit group 기준으로 벌금 조회 Slice )</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 6. /my-fit-service/penalties/filters/by-fit-group/{fit-group-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-group-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">penalty list를 조회할 fit group id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">GET /my-fit-service/penalties/filters/by-fit-group/1634?startDate=2024-04-01T00:00:00Z&amp;endDate=2024-04-30T23:59:59Z&amp;onlyPaid=true&amp;onlyNotPaid=false&amp;pageNumber=0&amp;pageSize=5 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Host: localhost:8080</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/penalties/filters/by-fit-group/1634?startDate=2024-04-01T00:00:00Z&amp;endDate=2024-04-30T23:59:59Z&amp;onlyPaid=true&amp;onlyNotPaid=false&amp;pageNumber=0&amp;pageSize=5' -i -X GET \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageNumber</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 페이지 번호</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>pageSize</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">조회 한 페이지 size</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>hasNext</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">다음 Slice가 있는지</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>totalAmount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">총 금액 ( 결과 리스트 안의 Amount의 sum이 아닌 조회 조건으로 Paging 되지 않은 끝 Row의 Amount sum )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[]</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Array</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 목록</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitPenaltyId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].fitGroupId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty가 발생한 fit group id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].userId</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty (벌금) 이 발부된 user id</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].amount</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Number</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 금액</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].paid</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 납부 했는지 여부</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].noNeedPay</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty 납부할 필요 없는지 여부 ( ex-fit reader가 취소시켰을때 true )</p></td>
</tr>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>content[].createdAt</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>String</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">fit penalty가 발생한 일시</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"pageNumber":0,"pageSize":5,"hasNext":false,"totalAmount":15000,"content":[{"fitPenaltyId":1,"fitGroupId":4,"userId":"testUserId1","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.935608300Z"},{"fitPenaltyId":2,"fitGroupId":5,"userId":"testUserId2","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.935608300Z"},{"fitPenaltyId":3,"fitGroupId":6,"userId":"testUserId3","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.935608300Z"}]}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 515
Content-Type: application/json

{"pageNumber":0,"pageSize":5,"hasNext":false,"totalAmount":15000,"content":[{"fitPenaltyId":1,"fitGroupId":4,"userId":"testUserId1","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.935608300Z"},{"fitPenaltyId":2,"fitGroupId":5,"userId":"testUserId2","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.935608300Z"},{"fitPenaltyId":3,"fitGroupId":6,"userId":"testUserId3","amount":5000,"paid":true,"noNeedPay":false,"createdAt":"2024-04-19T08:17:31.935608300Z"}]}</code></pre>
</div>
</div>
</div>
</div>
</div>
<div class="sect1">
<h2 id="_6_fit_management"><a class="link" href="#_6_fit_management">6. fit management</a></h2>
<div class="sectionbody">
<div class="sect2">
<h3 id="_fit_management_fit_penalty_paid_by_fit_leader_패널티_금액_리더가_입금_완료_처리"><a class="link" href="#_fit_management_fit_penalty_paid_by_fit_leader_패널티_금액_리더가_입금_완료_처리">fit management fit penalty paid by fit leader ( 패널티 금액 리더가 입금 완료 처리 )</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 7. /my-fit-service/management/penalties/{fit-penalty-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-penalty-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">입금 완료 처리할 fit penalty id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">PUT /my-fit-service/management/penalties/162 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 30
Host: localhost:8080

{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/management/penalties/162' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId"}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isPaidSuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">수납 완료 등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isPaidSuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 22
Content-Type: application/json

{"isPaidSuccess":true}</code></pre>
</div>
</div>
</div>
<div class="sect2">
<h3 id="_fit_management_fit_penalty_no_need_pay_by_fit_leader_패널티_금액_리더가_입금_불필요_처리"><a class="link" href="#_fit_management_fit_penalty_no_need_pay_by_fit_leader_패널티_금액_리더가_입금_불필요_처리">fit management fit penalty no need pay by fit leader ( 패널티 금액 리더가 입금 불필요 처리 )</a></h3>
<div class="paragraph">
<p>path-parameters</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<caption class="title">Table 8. /my-fit-service/management/penalties/{fit-penalty-id}</caption>
<colgroup>
<col style="width: 50%;">
<col style="width: 50%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Parameter</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>fit-penalty-id</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">입금 불필요 처리할 fit penalty id</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>http-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">DELETE /my-fit-service/management/penalties/162 HTTP/1.1
Content-Type: application/json;charset=UTF-8
Accept: application/json
Content-Length: 30
Host: localhost:8080

{"requestUserId":"testUserId"}</code></pre>
</div>
</div>
<div class="paragraph">
<p>curl-request</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight"><code data-lang="bash" class="language-bash hljs">$ curl 'http://localhost:8080/my-fit-service/management/penalties/162' -i -X DELETE \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -H 'Accept: application/json' \
    -d '{"requestUserId":"testUserId"}'</code></pre>
</div>
</div>
<div class="paragraph">
<p>response-fields</p>
</div>
<table class="tableblock frame-all grid-all stretch">
<colgroup>
<col style="width: 33.3333%;">
<col style="width: 33.3333%;">
<col style="width: 33.3334%;">
</colgroup>
<thead>
<tr>
<th class="tableblock halign-left valign-top">Path</th>
<th class="tableblock halign-left valign-top">Type</th>
<th class="tableblock halign-left valign-top">Description</th>
</tr>
</thead>
<tbody>
<tr>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>isNoNeedPaySuccess</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock"><code>Boolean</code></p></td>
<td class="tableblock halign-left valign-top"><p class="tableblock">입금 불필요 등록 성공 여부</p></td>
</tr>
</tbody>
</table>
<div class="paragraph">
<p>response-body</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="json" class="language-json hljs">{"isNoNeedPaySuccess":true}</code></pre>
</div>
</div>
<div class="paragraph">
<p>http-response</p>
</div>
<div class="listingblock">
<div class="content">
<pre class="highlightjs highlight nowrap"><code data-lang="http" class="language-http hljs">HTTP/1.1 200 OK
Content-Length: 27
Content-Type: application/json

{"isNoNeedPaySuccess":true}</code></pre>
</div>
</div>
</div>
</div>
</div>
</div>
<div id="footer">
<div id="footer-text">
Version 0.0.1-SNAPSHOT<br>
Last updated 2024-04-19 17:15:38 +0900
</div>
</div>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.6/styles/github.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.15.6/highlight.min.js"></script>
<script>hljs.initHighlighting()</script>
</body>
</html>
