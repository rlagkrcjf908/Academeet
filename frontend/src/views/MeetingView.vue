<template>
  <div id="main-container" class="container">
    <!-- 회의 입장 후 화면 -->
    <div class="common-layout">
      <el-container class="meetting-view-container">
        <el-container>
          <el-main>
            <!-- 비디오 화면 -->
            <!-- 내 메인 화면 -->
            <el-container
              style="width: 100%; display: inline-block"
              max-height="100px"
            >
              <el-scrollbar>
                <div class="scrollbar-flex-content">
                  <!-- 내 개인화면 -->
                  <user-video
                    class="my-video"
                    :stream-manager="PublisherCamera"
                    :role="publisher"
                    :faceDetection="onFaceDetection"
                    @click="updateMainVideoStreamManager(PublisherCamera)"
                  />
                  <!-- 게스트 -->
                  <user-video
                    class="other-video"
                    v-for="sub in SubscribersCamera"
                    :key="sub.stream.connection.connectionId"
                    :stream-manager="sub"
                    :role="subscriber"
                    @click="updateMainVideoStreamManager(sub)"
                  />
                </div>
              </el-scrollbar>
            </el-container>
            <div id="video-container" class="col-md-6">
              <div id="main-video" class="my-main-container">
                <user-video
                  class="my-main"
                  :stream-manager="mainStreamManager"
                />
              </div>
            </div>
          </el-main>

          <!--gaj-->
          <el-footer>
            <div>
              <div class="meeting-btn">
                <!-- 마이크 오프 -->
                <button
                  data-tooltip="마이크 켜기"
                  class="meeting-bnt-item"
                  v-if="!audioEnabled"
                  @click="audioTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/external-zen-filled-royyan-wijaya/48/FA5252/external-mic-off-audio-and-video-zen-filled-royyan-wijaya.png"
                  />
                </button>
                <!-- 마이크 온 -->
                <button
                  data-tooltip="마이크 끄기"
                  class="meeting-bnt-item"
                  v-if="audioEnabled"
                  @click="audioTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/sf-black-filled/64/12B886/microphone.png"
                  />
                </button>
                <!-- 비디오 오프 -->
                <button
                  data-tooltip="비디오 켜기"
                  class="meeting-bnt-item"
                  v-if="!videoEnabled"
                  @click="videoTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/ios-filled/50/FA5252/no-video--v1.png"
                  />
                </button>
                <!-- 비디오 온 -->
                <button
                  data-tooltip="비디오 끄기"
                  class="meeting-bnt-item"
                  v-if="videoEnabled"
                  @click="videoTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/external-tanah-basah-glyph-tanah-basah/48/12B886/external-video-social-media-ui-tanah-basah-glyph-tanah-basah.png"
                  />
                </button>
                <!-- 화면공유-->
                <button
                  data-tooltip="화면 공유"
                  class="meeting-bnt-item"
                  id="buttonScreenShare"
                  @click="publishScreenShare"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/material-outlined/48/12B886/imac.png"
                  />
                </button>
                <!-- 그룹 호스트 권한 버튼 -->
                <!-- 회의 녹화 시작 -->
                <button
                  data-tooltip="회의 녹화 시작"
                  class="meeting-bnt-item"
                  v-if="!recodingEnabled && userId == ownerId"
                  @click="recordingTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/external-tanah-basah-glyph-tanah-basah/96/FA5252/external-recording-multimedia-tanah-basah-glyph-tanah-basah.png"
                  />
                </button>
                <!-- 회의 녹화 끝 -->
                <button
                  data-tooltip="회의 녹화 끝"
                  class="meeting-bnt-item"
                  v-if="recodingEnabled && userId == ownerId"
                  @click="recordingTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/external-tanah-basah-glyph-tanah-basah/96/FA5252/external-rec-video-and-movie-tanah-basah-glyph-tanah-basah-2.png"
                  />
                </button>
                <!-- 음성 기록 시작 -->
                <button
                  data-tooltip="음성 기록 시작"
                  class="meeting-bnt-item"
                  v-if="!speechEnabled && userId == ownerId"
                  @click="speechingTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/ios-glyphs/60/737373/voice-recognition-scan.png"
                  />
                </button>
                <!-- 음성 기록 끝 -->
                <button
                  data-tooltip="음성 기록 끝"
                  class="meeting-bnt-item"
                  v-if="speechEnabled && userId == ownerId"
                  @click="speechingTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/ios-glyphs/30/12B886/voice-recognition-scan.png"
                  />
                </button>
                <!-- 출석체크 시작-->
                <button
                  data-tooltip="출석체크 시작"
                  class="meeting-bnt-item"
                  v-if="!onFaceDetection && userId == ownerId"
                  @click="checkingTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/ios-filled/50/737373/attendance-mark.png"
                  />
                </button>
                <!-- 출석체크 끝-->
                <button
                  data-tooltip="출석체크 끝"
                  class="meeting-bnt-item"
                  v-if="onFaceDetection && userId == ownerId"
                  @click="checkingTrigger()"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/ios-filled/50/12B886/attendance-mark.png"
                  />
                </button>
                <!-- 전체 오디오 온 -->
                <button
                  data-tooltip="전체 오디오 온"
                  class="meeting-bnt-item"
                  v-if="!masterAudioEnabled && userId == ownerId"
                  @click="audioOn"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/external-icongeek26-glyph-icongeek26/64/FA5252/external-mute-music-icongeek26-glyph-icongeek26.png"
                  />
                </button>
                <!-- 전체 오디오 오프-->
                <button
                  data-tooltip="전체 오디오 오프"
                  class="meeting-bnt-item"
                  v-if="masterAudioEnabled && userId == ownerId"
                  @click="audioOff"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/sf-regular-filled/48/12B886/high-volume.png"
                  />
                </button>
                <!-- 전체 비디오 온 -->
                <button
                  data-tooltip="전체 비디오 온"
                  class="meeting-bnt-item"
                  v-if="!masterVideoEnabled && userId == ownerId"
                  @click="videoOn"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/ios-filled/50/FA5252/stop-gesture.png"
                  />
                </button>
                <!-- 전체 비디오 오프-->
                <button
                  data-tooltip="전체 비디오 오프"
                  class="meeting-bnt-item"
                  v-if="masterVideoEnabled && userId == ownerId"
                  @click="videoOff"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/pastel-glyph/64/12B886/laptop-play-video--v2.png"
                  />
                </button>
                <!-- 회의 나가기 -->
                <button
                  data-tooltip="회의 나가기"
                  class="meeting-bnt-item"
                  v-if="userId !== ownerId"
                  @click="leaveSession"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/sf-black-filled/64/FA5252/x.png"
                  />
                </button>
                <!-- 세션 종료하기 -->
                <button
                  data-tooltip="세션 종료하기"
                  class="meeting-bnt-item"
                  v-if="userId == ownerId"
                  @click="endSession"
                >
                  <img
                    class="meeting-btn-item-img"
                    src="https://img.icons8.com/sf-black-filled/64/FA5252/x.png"
                  />
                </button>
                <!-- 파일다운받기 -->
                <!-- <button data-tooltip="파일 다운" class="meeting-bnt-item">
                      <img class="meeting-btn-item-img" src="https://img.icons8.com/windows/64/1A1A1A/file-download.png"/>
                    </button> -->
              </div>
            </div>
          </el-footer>
          <!-- kaj -->

          <!-- ljy -->

          <!-- ljy -->
        </el-container>

        <!-- 사이드바 -->
        <el-aside class="meet-view-side">
          <div class="common-layout meet-side">
            <el-container>
              <el-header class="meet-side-header">
                <h3 id="session-title">[ {{ mySessionTitle }} ]</h3>
              </el-header>
              <hr style="border: 1px solid white; margin: 0" />

              <el-main class="meet-side-main" v-show="isChatActive">
                <div id="chatting-content" style="display: inline-block">
                  - Chatting -
                </div>
              </el-main>
              <el-main class="meet-side-main" v-show="isSpeechActive">
                <div id="speech-content" style="display: inline-block">
                  - Speech -
                </div>
              </el-main>

              <el-footer class="meet-side-footer">
                <div class="insert-chatting" v-if="isChatActive">
                  <input
                    required
                    type="text"
                    style="margin-right: 5px; height: 25px; width: 180px"
                    v-model="message"
                    @keydown.enter="sendChat()"
                  />
                  <button
                    class="meet-chat-send-btn"
                    style="height: 32px; width: 50px"
                    @click="sendChat()"
                  >
                    입력
                  </button>
                </div>
                <button
                  class="meet-chat-ctrl-btn"
                  :class="{ active: isChatActive }"
                  @click="toggle"
                >
                  {{ isChatActive ? "Go Speech" : "Go Chatting" }}
                </button>
              </el-footer>
            </el-container>
          </div>
        </el-aside>
      </el-container>
    </div>

    <!-- <a id="playVideo" :href=this.videoURL>Video</a> -->
  </div>
  <div id="screens">
    <!-- 스크린 공유 화면 -->

    <div id="screen-container" class="col-md-6">
      <h2>Screen Share</h2>
      <!-- 호스트 -->
      <user-video
        :stream-manager="PublisherScreen"
        @click="updateMainVideoStreamManager(PublisherScreen)"
      />
      <!-- 게스트 -->
      <user-video
        v-for="sub in SubscribersScreen"
        :key="sub.stream.connection.connectionId"
        :stream-manager="sub"
        @click="updateMainVideoStreamManager(sub)"
      />
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import UserVideo from "../components/meeting/UserVideo";
import { meetingCreate } from "@/common/api/meetingAPI";
// import SpeechRecognition from "./components/SpeechRecognition";
//import * as faceapi from 'face-api.js';
axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL = "https://i8d108.p.ssafy.io";
const OPENVIDU_SERVER_URL = "https://i8d108.p.ssafy.io:8443";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "App",

  components: {
    UserVideo,
    // SpeechRecognition,
  },

  data() {
    const meetInfo = JSON.parse(sessionStorage.getItem("meetInfo"));
    // console.log("meetInfo: ", meetInfo);
    return {
      publisher: "publisher",
      subscriber: "subscriber",
      isChatActive: true,
      isSpeechActive: false,

      // OpenVidu objects
      //OV: undefined,
      //session: undefined,
      mainStreamManager: undefined,
      PublisherCamera: undefined,
      PublisherScreen: undefined,
      SubscribersCamera: [],
      SubscribersScreen: [],

      // Audio, Video Control
      videoEnabled: true,
      audioEnabled: true,

      // Master Audio, Video Control
      masterVideoEnabled: true,
      masterAudioEnabled: true,

      // chatting
      message: "",
      chats: [],

      // screen share
      OVCamera: undefined,
      OVScreen: undefined,
      sessionCamera: undefined,
      sessionScreen: undefined,
      screensharing: false,

      // Join form
      ownerId: meetInfo.ownerId,
      userId: meetInfo.userId,
      mySessionTitle: meetInfo.meetTitle,
      // SessionId 는 무적권 알파벳과 숫자만
      mySessionId: String(meetInfo.meetId),
      myUserName: meetInfo.userName,

      // recording
      hasAudio: true,
      hasVideo: true,
      outputMode: ["COMPOSED", "INDIVIDUAL", "COMPOSED_QUICK_START"],
      recordingMode: ["ALWAYS", "MANUAL"],
      recordingId: undefined,
      videoURL: undefined,
      recodingEnabled: false,

      // face detection
      interval: undefined,
      onFaceDetection: false,

      // Speech
      speechEnabled: false,
      speechRecognition: undefined,
      recognizedText: "",
      recognizedlog: [],
    };
  },

  mounted: function () {
    var SpeechRecognition =
      window.SpeechRecognition || window.webkitSpeechRecognition;
    this.speechRecognition = new SpeechRecognition();
    this.speechRecognition.lang = "ko-KR";
    this.speechRecognition.continuous = true;
    this.speechRecognition.maxAlternatives = 10000;
    this.joinSession();
  },

  methods: {
    // fileDownload(){
    //   axios.get('https://i8d108.p.ssafy.io/stt/testf3491234-a387-4566-a5ec-4aca2a1d287a.txt', {
    //     responseType: "blob"
    //   }).then(response => {
    //     const url = window.URL.createObjectURL(new Blob([response.data]));
    //     const link = document.createElement('a');
    //     link.href = url;
    //     link.setAttribute('download', 'test.txt'); //or any other extension
    //     document.body.appendChild(link);
    //     link.click();
    //   }).catch((error) => {
    //     console.log(error);
    //     alert("파일 다운로드 실패");
    //   });
    // },

    toggle() {
      (this.isChatActive = !this.isChatActive),
        (this.isSpeechActive = !this.isSpeechActive);
    },

    joinSession() {
      // --- *1) Create two OpenVidu objects.
      // 'OVCamera' will handle Camera operations.
      // 'OVScreen' will handle screen sharing operations
      this.OVCamera = new OpenVidu();
      this.OVScreen = new OpenVidu();

      // --- *2) Init two OpenVidu Session Objects ---

      // 'sessionCamera' will handle camera operations
      // 'sessionScreen' will handle screen sharing operations
      this.sessionCamera = this.OVCamera.initSession();
      this.sessionScreen = this.OVScreen.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.sessionCamera.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "CAMERA") {
          const subscriber = this.sessionCamera.subscribe(stream);
          this.SubscribersCamera.push(subscriber);
        }
      });

      this.sessionScreen.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "SCREEN") {
          const subscriber = this.sessionScreen.subscribe(stream);
          this.SubscribersScreen.push(subscriber);
        }
      });

      // On every Stream destroyed...
      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        const index = this.SubscribersCamera.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.SubscribersCamera.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      this.sessionCamera.on("signal:my-chat", (event) => {
        //this.chats.push(JSON.parse(event.data));
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")

        let receive = event.data.split("/");
        let userName = receive[0];
        let message = receive[1];
        document.getElementById(
          "chatting-content"
        ).innerHTML += `<p>${userName}: ${message}</p>`;
        // document.getElementById(
        //   "chatting-content"
        // ).innerHTML += `<p>${message}</p>`;
      });

      this.sessionCamera.on("signal:my-speech", (event) => {
        //this.chats.push(JSON.parse(event.data));
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")

        let receive = event.data.split("/");
        let userName = receive[0];
        let message = receive[1];
        document.getElementById(
          "speech-content"
        ).innerHTML += `<p>${userName}: ${message}</p>`;

        // document.getElementById(
        //   "speech-content"
        // ).innerHTML += `<p>${message}</p>`;
      });

      this.sessionCamera.on("signal:master-audio-on", (event) => {
        console.log(event.type); // The type of message ("my-chat")
        this.audioEnabled = true;
        this.PublisherCamera.publishAudio(this.audioEnabled);
      });

      this.sessionCamera.on("signal:master-audio-off", (event) => {
        console.log(event.type); // The type of message ("my-chat")
        this.audioEnabled = false;
        this.PublisherCamera.publishAudio(this.audioEnabled);
      });

      this.sessionCamera.on("signal:master-video-on", (event) => {
        console.log(event.type); // The type of message ("my-chat")
        this.vedioEnabled = true;
        this.PublisherCamera.publishVideo(this.vedioEnabled);
      });

      this.sessionCamera.on("signal:master-video-off", (event) => {
        console.log(event.type); // The type of message ("my-chat")
        this.vedioEnabled = false;
        this.PublisherCamera.publishVideo(this.vedioEnabled);
      });

      this.sessionCamera.on("signal:end-session", (event) => {
        console.log(event.type); // The type of message ("my-chat")
        this.leaveSession();
      });

      this.sessionCamera.on("signal:checking", (event) => {
        console.log(event);
        console.log(event.type); // The type of message ("my-chat")

        if (event.data == "true") {
          this.onFaceDetection = true;
        } else {
          this.onFaceDetection = false;
        }
      });

      this.sessionCamera.on("signal:speeching", (event) => {
        console.log(event);
        console.log(event.type); // The type of message ("my-chat")

        if (event.data == "true") {
          this.startSpeeching();
        } else {
          this.stopSpeeching();
        }
      });

      this.sessionCamera.on("publisherStartSpeaking", (event) => {
        console.log(
          "User " + event.connection.connectionId + " start speaking"
        );
      });

      this.sessionCamera.on("publisherStopSpeaking", (event) => {
        console.log("User " + event.connection.connectionId + " stop speaking");
      });

      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      this.getToken(this.mySessionId).then((token) => {
        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.sessionCamera
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: true, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publisher

            this.mainStreamManager = publisher;
            this.PublisherCamera = publisher;

            // --- 6) Publish your stream ---

            this.sessionCamera.publish(this.PublisherCamera);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      this.getToken(this.mySessionId).then((tokenScreen) => {
        // Create a token for screen share
        console.log("얍", tokenScreen, this.myUserName);
        this.sessionScreen
          .connect(tokenScreen, { clientData: this.myUserName })
          .then(() => {
            document.getElementById("buttonScreenShare").style.visibility =
              "visible";
            console.log("Session screen connected");
          })
          .catch((error) => {
            console.warn(
              "There was an error connecting to the session for screen share:",
              error.code,
              error.messag
            );
          });
      });

      this.recognizedlog = [];
      window.addEventListener("beforeunload", this.leaveSession);
    },

    publishScreenShare() {
      // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
      var publisherScreen = this.OVScreen.initPublisher(undefined, {
        videoSource: "screen",
        publishAudio: false,
      });

      // --- 9.2) Publish the screen share stream only after the user grants permission to the browser
      publisherScreen.once("accessAllowed", () => {
        document.getElementById("buttonScreenShare").style.visibility =
          "hidden";
        this.screensharing = true;
        // If the user closes the shared window or stops sharing it, unpublish the stream
        publisherScreen.stream
          .getMediaStream()
          .getVideoTracks()[0]
          .addEventListener("ended", () => {
            console.log('User pressed the "Stop sharing" button');
            this.sessionScreen.unpublish(publisherScreen);
            document.getElementById("buttonScreenShare").style.visibility =
              "visible";
            this.screensharing = false;
          });

        this.PublisherScreen = publisherScreen;
        this.sessionScreen.publish(publisherScreen);
      });
      /*
      publisherScreen.on('videoElementCreated', function (event) {
        appendUserData(event.element, sessionScreen.connection);
        event.element['muted'] = true;
      });
      */
      publisherScreen.once("accessDenied", () => {
        console.error("Screen Share: Access Denied");
      });
    },

    leaveSession() {
      axios({
        url: "https://i8d108.p.ssafy.io/api/v1/meet/recognize",
        method: "post",
        data: {
          stt: this.recognizedlog,
        },
      })
        .then(() => {
          this.$router.push({
            name: "listMain",
          });
        })
        .catch(function (error) {
          console.log(error);
        });

      // --- 7) Leave the session by calling 'disconnect' method OVCameraer the Session object ---
      if (this.sessionCamera) this.sessionCamera.disconnect();
      if (this.sessionScreen) this.sessionScreen.disconnect();

      // Empty all properties...
      this.sessionCamera = undefined;
      this.sessionScreen = undefined;
      this.mainStreamManager = undefined;
      this.PublisherCamera = undefined;
      this.PublisherScreen = undefined;
      this.SubscribersCamera = [];
      this.SubscribersScreen = [];
      this.OVCamera = undefined;
      this.OVScreen = undefined;

      this.screensharing = false;

      document.getElementById("chatting-content").innerHTML = "";

      document.getElementById("speech-content").innerHTML = "";

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;

      this.mainStreamManager = null;
      setTimeout(() => (this.mainStreamManager = stream), 100);

      /*
      if(this.mainStreamManager !== null) this.mainStreamManager = null;
      else this.mainStreamManager = stream;
      */
    },

    videoTrigger() {
      this.videoEnabled = !this.videoEnabled;
      this.PublisherCamera.publishVideo(this.videoEnabled);
    },

    videoOn() {
      this.masterVideoEnabled = true;
      this.sessionCamera
        .signal({
          to: [],
          type: "master-video-on",
        })
        .then(() => {
          console.log("master-video-on successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    videoOff() {
      this.masterVideoEnabled = false;
      this.sessionCamera
        .signal({
          to: [],
          type: "master-video-off",
        })
        .then(() => {
          console.log("master-video-off successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    audioTrigger() {
      this.audioEnabled = !this.audioEnabled;
      this.PublisherCamera.publishAudio(this.audioEnabled);
    },

    audioOn() {
      this.masterAudioEnabled = true;
      this.sessionCamera
        .signal({
          to: [],
          type: "master-audio-on",
        })
        .then(() => {
          console.log("master-audio-on successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    audioOff() {
      this.masterAudioEnabled = false;
      this.sessionCamera
        .signal({
          to: [],
          type: "master-audio-off",
        })
        .then(() => {
          console.log("master-audio-off successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    endSession() {
      this.sessionCamera
        .signal({
          to: [],
          type: "end-session",
        })
        .then(() => {
          this.$router.push({
            name: "listMain",
          });
          console.log("end-session successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    sendChat() {
      /*
      const sendData = {
        nickname: this.myUserName,
        msg: this.message,
      };
      */
      this.sessionCamera
        .signal({
          data: this.myUserName + "/" + this.message,
          // data: JSON.stringify(sendData),
          to: [],
          type: "my-chat",
        })
        .then(() => {
          console.log("Message successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
      this.message = "";
    },

    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) =>
        this.createToken(sessionId)
      );
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      console.log("seesionId", sessionId);
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection

    recordingTrigger() {
      this.recordingEnabled = !this.recodingEnabled;
      if (this.recodingEnabled) {
        console.log("Starting recording");
        this.recordingStartFunc(this.mySessionId).then((recordingId) => {
          console.log("Recording ID", recordingId);
          this.recordingId = recordingId;
        });
      } else {
        console.log("Stop recording");
        this.recordingStopFunc(this.recordingId).then((url) => {
          this.videoURL = url;
        });
      }
    },

    recordingStartFunc(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/recordings/start`,
            JSON.stringify({
              //BODY
              session: sessionId,
              outputMode: this.outputMode[0],
              hasAudio: this.hasAudio,
              hasVideo: this.hasVideo,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => reject(error.response));
      });
    },

    recordingStopFunc(recordingId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/recordings/stop/${recordingId}`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            }
          )
          .then((response) => response.data)
          .then((data) => resolve(data.url))
          .catch((error) => reject(error.response));
      });
    },

    checkingTrigger() {
      this.sessionCamera
        .signal({
          data: !this.onFaceDetection,
          to: [],
          type: "checking",
        })
        .then(() => {
          console.log("checking successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    // async startChecking(){
    // this.onFaceDetection = true,
    // document.getElementById('buttonStartPresent').style.visibility = "hidden";
    // document.getElementById('buttonStopPresent').style.visibility = "visible";
    // },

    // async stopChecking(){
    // this.onFaceDetection = false,
    // document.getElementById('buttonStartPresent').style.visibility = "visible";
    // document.getElementById('buttonStopPresent').style.visibility = "hidden";
    // },

    speechingTrigger() {
      this.sessionCamera
        .signal({
          data: !this.speechEnabled,
          to: [],
          type: "speeching",
        })
        .then(() => {
          console.log("speechingTrigger successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });
    },

    async startSpeeching() {
      // this.onFaceDetection = true,
      console.log("startSpeeching");
      this.speechEnabled = true;
      // this.speechRecognition.onresult = (event) => {
      //     this.recognizedText = this.recognizedText + " " + event.results[0][0].transcript;
      // };

      this.speechRecognition.onresult = (event) => {
        console.log("speechRecognition successfully!!");
        console.log(event);
        for (
          let i = event.resultIndex, len = event.results.length;
          i < len;
          i++
        ) {
          let transcript = event.results[i][0].transcript;
          console.log(transcript);
          this.recognizedText += transcript;
          if (event.results[i].isFinal) {
            this.sendSpeech();
          }
        }
        // this.recognizedText = this.recognizedText + " " + event.results[0][0].transcript;
      };

      this.speechRecognition.start();
    },

    async stopSpeeching() {
      // this.onFaceDetection = false,
      console.log("stopSpeeching");
      this.speechEnabled = false;

      // this.sendSpeech();
      this.speechRecognition.stop();

      this.recognizedText = "";
    },

    sendSpeech() {
      /*
      const sendData = {
          nickname: this.myUserName,
          msg: this.message,
      };
      */
      this.sessionCamera
        .signal({
          data: this.myUserName + "/" + this.recognizedText,
          // data: JSON.stringify(sendData),
          to: [],
          type: "my-speech",
        })
        .then(() => {
          console.log("Speech successfully sent");
        })
        .catch((error) => {
          console.error(error);
        });

      //this.recognizedlog[n++]=this.myUserName+" : "+this.recognizedText;
      this.recognizedlog.push(this.myUserName + " : " + this.recognizedText);
      this.recognizedText = "";
    },
  },
};
</script>

<style>
.meeting-bnt-item {
  border: none;
  background-color: white;
  border-radius: 100%;
  width: 40px;
  height: 40px;
  margin: 2em;
  margin: auto;
}
.meeting-bnt-leave {
  border: none;
  background-color: rgb(216, 3, 3);
  color: white;
  font-size: 2em;
  border-radius: 8px;
  height: 56px;
  margin: 2em;
  margin: auto;
}

.meeting-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  background-color: rgba(91, 88, 88, 1);
  padding: 1em;
}
.meeting-btn-item-img {
  height: 20px;
  width: 20px;
}
.meet-view-side {
  overflow: hidden;
  margin: 10px;
  width: 15vw;
}
.meet-side-main {
  background-color: white;
  margin: 10px;
  height: 700px;
  word-break: break-all;
  /* overflow: hidden; */
}
.meet-chat-send-btn {
  width: 50px;
  height: 30px;
  background-color: #95d475;
  border: none;
  font-size: 15px;
  color: white;
}
.meet-chat-send-btn:hover {
  background-color: #d1edc4;
  border: none;
  font-size: 15px;
  color: #5aba2a;
}
.meet-chat-ctrl-btn {
  width: 230px;
  height: 35px;
  margin: 10px;
  background-color: #eebe77;
  border: none;
  font-size: 20px;
  color: white;
  border-radius: 50px;
}
.meet-chat-ctrl-btn:hover {
  width: 230px;
  height: 35px;
  margin: 10px;
  background-color: #f8e3c5;
  border: none;
  font-size: 20px;
  color: #fdb447;
  border-radius: 50px;
}
::-webkit-scrollbar {
  width: 6px;
}
::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}
::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}

[data-tooltip] {
  position: relative;
}
[data-tooltip]:before,
[data-tooltip]:after {
  visibility: hidden;
  opacity: 0;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  white-space: nowrap;
  transition: all 0.2s ease;
  font-size: 11px;
  font-family: dotum;
  letter-spacing: -1px;
}
[data-tooltip]:before {
  content: attr(data-tooltip);
  height: 13px;
  position: absolute;
  top: -20px;
  padding: 5px 10px;
  border-radius: 5px;
  color: #fff;
  background: #2a2a2a;
  box-shadow: 0 3px 8px rgba(165, 165, 165, 0.5);
}
[data-tooltip]:after {
  content: "";
  border-left: 5px solid transparent;
  top: 2px;
  border-right: 5px solid transparent;
  border-top: 5px solid #2a2a2a;
}
[data-tooltip]:not([data-tooltip=""]):hover:before {
  visibility: visible;
  opacity: 1;
  top: -30px;
}
[data-tooltip]:not([data-tooltip=""]):hover:after {
  visibility: visible;
  opacity: 1;
  top: -8px;
}
.my-main-container {
  align-items: center;
  justify-content: center;
}

.my-video {
  background-color: aqua;
  flex-shrink: 0;
  align-items: center;
  justify-content: center;
  width: 160px;
  height: 90px;
  margin: 5px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-danger-light-9);
  color: var(--el-color-danger);
}

.other-video {
  background-color: #c71100;
  flex-shrink: 0;
  align-items: center;
  justify-content: center;
  width: 160px;
  height: 90px;
  margin: 5px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-danger-light-9);
  color: var(--el-color-danger);
}

.my-main {
  width: 960px;
  height: 540px;
  align-items: center;
}

.scrollbar-flex-content {
  display: flex;
  height: 120px;
}
.scrollbar-demo-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-danger-light-9);
  color: var(--el-color-danger);
}

.nickname {
  background: rgba(58, 64, 74, 0.651);
  padding: 5px !important;
  position: absolute;
  z-index: 999;
  color: #ffffff;
}
.form-control {
  color: black;
}

.pointer {
  cursor: pointer;
}

#closeButton {
  position: absolute;
  top: -3px;
  right: 0;
  z-index: 999;
}

#name-error-text {
  color: #fd6d5f;
  font-weight: bold;
  text-align: center;
}

#nicknameForm {
  padding: 10px;
}
.fullscreen {
  top: 40px;
}

.streamComponent {
  height: 100%;
}

video {
  -o-object-fit: cover;
  object-fit: cover;
  width: 100%;
  height: 100%;
  color: #ffffff;
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  font-family: Arial, Helvetica, sans-serif;
}

#statusIcons {
  bottom: 0;
  background: #c71100;
  width: 40px;
  height: fit-content;
  position: absolute;
  color: #ffffff;
}

#camIcon,
#micIcon {
  text-align: center;
  padding: 6px;
}

#fullscreenButton {
  position: absolute;
  bottom: 1px;
  right: 1px;
  z-index: 1000;
  background-color: #000000c4;
}
#volumeButton {
  background-color: #000000c4;
  position: absolute;
  bottom: 45px;
  right: 1px;
  z-index: 1000;
  color: #ffffff;
}
/* Contains the video element, used to fix video letter-boxing */
.OT_widget-container {
  width: 100%;
  height: 100%;
  position: absolute;
  overflow: hidden;
}

#input,
#label {
  color: white;
}
</style>
