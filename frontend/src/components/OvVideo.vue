<template>
	<video id="video" autoplay/>
</template>

<script>
import * as faceapi from 'face-api.js';
let count = 0;

export default {
	name: 'OvVideo',
	data(){
		return{
			interval : undefined,
		}
	},
	props: {
		streamManager: Object,
		role: String,
		faceDetection: Boolean
	},

	mounted () {
		/*
		console.log('OvVideo is mounted');
		console.log(this.streamManager);
		if(this.role === "publisher") console.log('Pusher');
		else if(this.role === "subscriber") console.log('Subscriber');
		else console.log('Noting');
		console.log("role : " + this.role);
		*/
		this.streamManager.addVideoElement(this.$el);

		this.initApi();
		//Promise.all([faceapi.nets.tinyFaceDetector.loadFromUri("/models"),])
	},

	methods:{
		async initApi(){
			await faceapi.nets.tinyFaceDetector.loadFromUri("/models");
		},
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
		async setAttendanceCount(){
			const name = this.client;
			const meetingAtt = count;
			
			const payload = {
				name,
				meetingAtt
			}
			console.log(payload);

        await this.$store.dispatch('meetingStore/setAttendanceCount', payload);
    	}
	},

	watch:{
		faceDetection() {			
			const video = document.getElementById("video");

			if(this.faceDetection){
				this.interval = setInterval(async () => {
					const detections = await faceapi
						.detectSingleFace(video, new faceapi.TinyFaceDetectorOptions()
					);
				
					const { clientData } =  this.getConnectionData();
					this.client = clientData;
					if (detections) {
						console.log(clientData + " : YES");
						count++;
						console.log(count);
					}
					else console.log(clientData+ " : NO");
				}, 1000);
			}else{
				clearInterval(this.interval);
			}
			
			console.log(this.client,count);
			this.setAttendanceCount();
			count = 0;
		}
	}
};
</script>
