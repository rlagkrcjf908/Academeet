<template>
	<div v-if="streamManager">
		<ov-video :stream-manager="streamManager" :role="role" :faceDetection="faceDetection"/>
		<div><p class="clientData">{{ clientData }}</p></div>
	</div>
	</template>
	
	<script>
	import OvVideo from './OvVideo';
	
	export default {
		name: 'UserVideo',
	
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
	
		methods: {
			getConnectionData () {
				const { connection } = this.streamManager.stream;
				return JSON.parse(connection.data);
			},
		},
	};
	</script>

	<style>
		.clientData{
			background-color: #61B299;
			margin: 0px;
		}
	</style>