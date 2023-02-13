<template>
    <div id="main-container" class="container">
      <!-- 회의 입장 전 화면 -->
      <div id="join" v-if="!sessionCamera">
      <!-- <div id="img-div">
        <img src="resources/images/openvidu_grey_bg_transp_cropped.png" />
      </div> -->
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
        <p>
          <label>회의제목</label>
          <h3>{{ mySessionTitle }}</h3>
          <!-- <input
          v-model="mySessionTitle"
          class="form-control"
          type="text"
          required
          /> -->
        </p>
        <p>
          <label>참가자</label>
          <h3>{{ myUserName }}</h3>
          <!-- <input
          v-model="myUserName"
          class="form-control"
          type="text"
          required
          /> -->
        </p>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="joinSession()">
          Join!
          </button>
        </p>
        </div>
      </div>
      </div>

      <!-- 회의 입장 후 화면 -->
      <div id="session" v-if="sessionCamera">
      <div id="session-header">

        <div class="common-layout">
          <el-container>
            <el-container>
              <el-header>
                <h1 id="session-title">{{ mySessionTitle }}</h1>
              </el-header>
              <el-main>
                <!-- 비디오 화면 -->
                <!-- 내 메인 화면 -->
                <div id="video-container" class="col-md-6">
                  <div id="main-video" class="col-md-6">
                    <user-video :stream-manager="mainStreamManager" />
                  </div>
                  <!-- 게스트 -->
                  <user-video
                  v-for="sub in SubscribersCamera"
                  :key="sub.stream.connection.connectionId"
                  :stream-manager="sub"
                  :role="subscriber"
                  @click="updateMainVideoStreamManager(sub)"
                  />
                </div>
              </el-main>
              <el-footer id="consoleBar">
                <!-- 음성 버튼 -->
                <button v-if="audioEnabled" type="button" @click="audioTrigger()">audio on</button>
                <button v-else type="button" @click="audioTrigger()">audio off</button>

                <!-- 비디오 버튼 -->
                <button v-if="videoEnabled" type="button" @click="videoTrigger()">video on</button>
                <button v-else type="button" @click="videoTrigger()">video off</button>
                
                <!-- Host-->
                <!-- 음성 버튼 -->
                <button type="button" @click="audioOn()">Master audio on</button>
                <button type="button" @click="audioOff()">Master audio off</button>

                <!-- 비디오 버튼 -->
                <button type="button" @click="videoOn()">Master video on</button>
                <button type="button" @click="videoOff()">Master video off</button>

                <!-- 회의녹화 -->
                <input
                class="btn btn-md"
                type="button"
                id="buttonStartRecording"
                @click="startRecording"
                value="Start recording"
                />
                <input
                class="btn btn-md"
                type="button"
                id="buttonStopRecording"
                @click="stopRecording"
                value="Stop recording"
                style="visibility: hidden"
                />
                <!-- 화면공유 -->
                <input
                class="btn btn-large"
                type="button"
                id="buttonScreenShare"
                @click="publishScreenShare"
                value="Screen share"
                style="visibility: hidden"
                />
                <!-- 세션 떠나기 -->
                <input
                class="btn btn-large btn-danger"
                type="button"
                id="buttonLeaveSession"
                @click="leaveSession"
                value="Leave session"
                />
                <!-- 세션 종료하기 -->
                <input
                class="btn btn-large btn-danger"
                type="button"
                id="buttonLeaveSession"
                @click="endSession"
                value="End session"
                />
                <!-- 출석 자동 체크 -->
                <input
                class="btn btn-md"
                type="button"
                id="buttonStartPresent"
                @click="startChecking"
                value="Start Checking"
                />

                <input
                class="btn btn-md"
                type="button"
                id="buttonStopPresent"
                @click="stopChecking"
                value="Stop Checking"
                style="visibility: hidden"
                />
                <!-- 음성기록 -->
                <input
                class="btn btn-md"
                type="button"
                id="buttonStartSpeech"
                @click="startSpeeching"
                value="Start Speeching"
                />
                <input
                class="btn btn-md"
                type="button"
                id="buttonStopSpeech"
                @click="stopSpeeching"
                value="Stop Speeching"
                style="visibility: hidden"
                />
                <input
                class="btn btn-md"
                type="button"

                @click="fileDownload"
                value="File Download"
                
                />
              </el-footer>
            </el-container>

            <!-- 사이드바 -->
            <el-aside width="200px">
              <el-container style="width:100%; display:inline-block" max-height="50px">
                <!-- 내 개인화면 -->
                <user-video
                  :stream-manager="PublisherCamera"
                  :role="publisher"
                  :faceDetection="onFaceDetection"
                  @click="updateMainVideoStreamManager(PublisherCamera)"
                />
              </el-container>

              <div id = "chat&STT">
                <!-- 채팅 창 -->
                <div id="chatting-content" style="width: 100%; display: inline-block" max-height="100px">Chatting</div>
                <div>
                  <!-- 채팅 보내기 -->
                  <input type="text" v-model="message" @keydown.enter="sendChat()" />
                  <button type="button" @click="sendChat()">입력</button>
                </div>
                <!-- 회의기록보기 창 -->
                <div id="speech-content" style="width: 30%; display: inline-block">Speech</div>
                <!-- <SpeechRecognition/> -->
                <!-- <textarea rows="10" v-model="recognizedText"></textarea> -->
              </div>
            </el-aside>

          </el-container>
        </div>

        <a id="playVideo" :href=this.videoURL>Video</a>

      </div>
        <div id = screens>


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
    </div>


    </div>
  </template>

  <script>
  import { mapState } from 'vuex';
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
  let n = 0;
  export default {
  name: "App",

  components: {
    UserVideo,
    // SpeechRecognition,
  },




  data() {
    const meetInfo = JSON.parse(sessionStorage.getItem("meetInfo"));
    console.log(meetInfo);
    return {
    publisher : "publisher",
    subscriber : "subscriber",

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
    mySessionTitle: meetInfo.meetTitle,
    // SessionId 는 무적권 알파벳과 숫자만
    mySessionId: String(meetInfo.meetId),
    myUserName: meetInfo.userName,

    // recording
    hasAudio : true,
    hasVideo : true,
    outputMode : ["COMPOSED", "INDIVIDUAL", "COMPOSED_QUICK_START"],
    recordingMode : ["ALWAYS", "MANUAL"],
    recordingId : undefined,
    videoURL : undefined,

    // face detection
    interval : undefined,
    onFaceDetection: false,

    // Speech
    speechEnabled: false,
    speechRecognition: undefined,
    recognizedText: "",
    recognizedlog:[]

    };
  },

  mounted: function() {
    var SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    this.speechRecognition = new SpeechRecognition();
    this.speechRecognition.lang = "ko-KR"
    this.speechRecognition.continuous = true;
    this.speechRecognition.maxAlternatives = 10000;

  },

  methods: {
    fileDownload(){
      axios.get('https://i8d108.p.ssafy.io/stt/testf3491234-a387-4566-a5ec-4aca2a1d287a.txt', {
        responseType: "blob"
      }).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'test.txt'); //or any other extension
        document.body.appendChild(link);
        link.click();
      }).catch((error) => {
        console.log(error);
        alert("파일 다운로드 실패");
      });
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

    this.sessionCamera.on('publisherStartSpeaking', (event) => {
      console.log('User ' + event.connection.connectionId + ' start speaking');

    });

    this.sessionCamera.on('publisherStopSpeaking', (event) => {
      console.log('User ' + event.connection.connectionId + ' stop speaking');
      if(this.speechEnabled){
        // this.sendSpeech();
        // this.speechRecognition.stop();
        // this.recognizedText = "";

        // this.speechRecognition.onresult = (event) => {
        //   this.recognizedText = this.recognizedText + " " + event.results[0][0].transcript;
        // };
        // this.speechRecognition.start();

        // this.stopSpeeching();
        // this.sendSpeech();
        // this.recognizedText = "";
        // this.startSpeeching();
      }
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
        mirror: false, // Whether to mirror your local video or not
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

    // this.getToken(this.mySessionId).then((tokenScreen) => {
    //   // Create a token for screen share
    //   this.sessionScreen
    //   .connect(tokenScreen, { clientData: this.myUserName })
    //   .then(() => {
    //     document.getElementById("buttonScreenShare").style.visibility =
    //     "visible";
    //     console.log("Session screen connected");
    //   })
    //   .catch((error) => {
    //     console.warn(
    //     "There was an error connecting to the session for screen share:",
    //     error.code,
    //     error.messag
    //     );
    //   });
    // });
    this.recognizedlog = [];
    window.addEventListener("beforeunload", this.leaveSession);
    },

    publishScreenShare() {
    // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
    var publisherScreen = this.OVScreen.initPublisher(undefined, {
      videoSource: "screen",
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
            url:'https://i8d108.p.ssafy.io/api/v1/meet/recognize',
            method:'post',
            data:{
                stt:this.recognizedlog
            }
        })
        .then(function a(response){
            console.log(response);
        })
        .catch(function(error){
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

    document.getElementById(
      "chatting-content"
      ).innerHTML = "";

    document.getElementById(
      "speech-content"
      ).innerHTML = "";

    // Remove beforeunload listener
    window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {

    if (this.mainStreamManager === stream) return;

    this.mainStreamManager = null;
    setTimeout(()=> this.mainStreamManager = stream, 100);

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

    audioOn(){
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
    audioOff(){
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

    endSession(){
        this.sessionCamera
        .signal({
            to: [],
            type: "end-session",
        })
        .then(() => {
            console.log("end-session successfully sent");
        })
        .catch((error) => {
        console.error(error);
        });
    },
    speechTrigger() {
    this.speechEnabled = !this.speechEnabled;
    // console.log("speechEnabled : " + this.speechEnabled);
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
      return this.createSession(mySessionId).then((sessionId) => this.createToken(sessionId));
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession (sessionId) {
      console.log("seesionId",sessionId)
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
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

    startRecording() {
        console.log("Starting recording");
        this.recordingStartFunc(this.mySessionId).then((recordingId) => {
                console.log("Recording ID", recordingId);
                this.recordingId = recordingId;
            }
        );
        document.getElementById('buttonStartRecording').style.visibility = "hidden";
        document.getElementById('buttonStopRecording').style.visibility = "visible";
    },

    recordingStartFunc(sessionId){
        return new Promise((resolve, reject) => {
            axios
            .post(
                `${OPENVIDU_SERVER_URL}/openvidu/api/recordings/start`,
                JSON.stringify({
                    //BODY
                    session: sessionId,
                    outputMode: this.outputMode[0],
                    hasAudio: this.hasAudio,
                    hasVideo: this.hasVideo
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

    stopRecording() {
        console.log("Stop recording");
        this.recordingStopFunc(this.recordingId).then((url) => {
                this.videoURL = url
            }
        );
        document.getElementById('buttonStartRecording').style.visibility = "visible";
        document.getElementById('buttonStopRecording').style.visibility = "hidden";
    },

    recordingStopFunc(recordingId){
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

    async startChecking(){
    this.onFaceDetection = true,
    document.getElementById('buttonStartPresent').style.visibility = "hidden";
    document.getElementById('buttonStopPresent').style.visibility = "visible";
    },

    async stopChecking(){
    this.onFaceDetection = false,
    document.getElementById('buttonStartPresent').style.visibility = "visible";
    document.getElementById('buttonStopPresent').style.visibility = "hidden";
    },

    async startSpeeching(){
    // this.onFaceDetection = true,
    console.log("startSpeeching");
    this.speechEnabled = true;
    // this.speechRecognition.onresult = (event) => {
    //     this.recognizedText = this.recognizedText + " " + event.results[0][0].transcript;
    // };

    
    this.speechRecognition.onresult = (event) => {
      for(let i = event.resultIndex, len = event.results.length; i < len; i++){
        let transcript = event.results[i][0].transcript;
        console.log(transcript);
        this.recognizedText += transcript;
        if(event.results[i].isFinal){ 
          this.sendSpeech();
        }
      }
        // this.recognizedText = this.recognizedText + " " + event.results[0][0].transcript;
    };

    this.speechRecognition.start();

    document.getElementById('buttonStartSpeech').style.visibility = "hidden";
    document.getElementById('buttonStopSpeech').style.visibility = "visible";
    },

    async stopSpeeching(){
    // this.onFaceDetection = false,
    console.log("stopSpeeching");
    this.speechEnabled = false;

    // this.sendSpeech();
    this.speechRecognition.stop();

    this.recognizedText = "";
    document.getElementById('buttonStartSpeech').style.visibility = "visible";
    document.getElementById('buttonStopSpeech').style.visibility = "hidden";
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
        this.recognizedlog.push(this.myUserName+" : "+this.recognizedText);
        this.recognizedText = "";

    },

  },

  };
  </script>
