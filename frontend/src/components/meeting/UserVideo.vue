<template>
	<div v-if="streamManager">
		<div class="userVideo_info">
			<div class="user-video">
				<ov-video :stream-manager="streamManager" :role="role" :faceDetection="faceDetection"/>
			</div>
			<div><p id="client-name" class="clientName">{{ clientData }}</p></div>
			<!-- <div id="clientName2"><p>{{ clientData }}</p></div>
			<div id="clientName3"><p>{{ clientData }}</p></div> -->
		</div>
	</div>
</template>

<script>
import * as faceapi from 'face-api.js';
import OvVideo from './OvVideo';


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
						document.getElementById("client-name").style.color = "black"
					}
					else 
							document.getElementById("client-name").style.color = "yellow"
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
	background-color: olivedrab;
	color: black;
}

.clientName {
  margin: 0px;
  position: absolute;
  top: 0px;
  left: 0px;
  display: inline-block;
  padding-left: 5px;
  padding-right: 5px;
  font-weight: bold;
  border-top-left-radius: 4px;
  border-bottom-right-radius: 4px;
}
</style>
