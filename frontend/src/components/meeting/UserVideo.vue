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
	.userVideo_info {
		position: relative;
	}

	.clientName{
		background-color: #61B299;
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