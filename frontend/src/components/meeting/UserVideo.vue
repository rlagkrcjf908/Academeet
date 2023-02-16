<template>
	<div v-if="streamManager">
		<div class="userVideo_info">
			<div class="user-video">
				<ov-video :stream-manager="streamManager" :role="role" :faceDetection="faceDetection"/>
			</div>
			<div><p class="clientName">{{ clientData }}</p></div>
		</div>
	</div>
</template>

<script>
import * as faceapi from 'face-api.js';
import OvVideo from './OvVideo';
var onCount = 1;
var allCount = 1;


export default {
	name: 'UserVideo',

	data(){
		return {
			liveInterval : undefined,
		}
	},

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
		role: String,
		faceDetection: Boolean,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	mounted () {
		this.initApi();
	},

	methods: {
		async initApi(){
			await faceapi.nets.tinyFaceDetector.loadFromUri("/models");
		},

		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},

	watch: {
		faceDetection() {
			const video = document.getElementById("video");

			if(this.faceDetection){
				this.liveInterval = setInterval(async () => {
					const detections = await faceapi
						.detectSingleFace(video, new faceapi.TinyFaceDetectorOptions()
					);

					const { clientData } =  this.getConnectionData();
					this.client = clientData;
					if (detections) {
						// console.log($store.state.setAttendanceAccount)
						console.log(clientData + " : YES1");
						console.log(onCount, "on");
						onCount++;
					}
					else
						console.log(allCount, "all");
						allCount++;
						console.log(onCount/allCount,"calculate")
						if (onCount/allCount < 0.7){
							document.getElementById("client-name").style.color = "coral"
						} else {
							document.getElementById("client-name").style.color = "#777777"
						};
						// working
				}, 1000);
			}else{
				clearInterval(this.liveInterval);
			}
		}
	}
};
</script>

<style>
.userVideo_info {
  position: relative;
	outline-color: azure;
	outline-width: 3px;
}

#client-name {
	background-color: white;
	color: #777777;
}

.clientName {
  background-color: white;
  margin: 0px;
  position: absolute;
  top: 0px;
  left: 0px;
  display: inline-block;
  padding-left: 5px;
  padding-right: 5px;
  color: #777777;
  font-weight: bold;
  border-top-left-radius: 4px;
  border-bottom-right-radius: 4px;
}
</style>
