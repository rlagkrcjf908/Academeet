<template>
  <div class="audiobox">
    <h1>Speech recognition Demo</h1>
    <h2>Supported: {{ speechRecognition != null }}</h2>
    <textarea rows="10" v-model="recognizedText"></textarea>
    <button type="button" @click="startRecording">Start recording</button>
  </div>
</template>

<script>
export default {
  name: "SpeechRecognition",
  data: function () {
    return {
      speechRecognition: undefined,
      recognizedText: "",
    };
  },
  methods: {
    startRecording: function () {
      this.speechRecognition.onresult = (event) => {
        this.recognizedText =
          this.recognizedText + " " + event.results[0][0].transcript;
      };
      // console.log(this.recognizedText);
      this.speechRecognition.start();
    },
  },
  mounted: function () {
    var SpeechRecognition =
      window.SpeechRecognition || window.webkitSpeechRecognition;
    this.speechRecognition = new SpeechRecognition();
  },
};
</script>

<style>
.audiobox {
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
}

.audiobox textarea {
  width: 50%;
  margin-bottom: 2em;
}
</style>
