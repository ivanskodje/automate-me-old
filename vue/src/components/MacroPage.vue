<template>
  <div>
    <h1>Automate Me</h1>
    <p>
      Click buttons below to set app badge count (calling Electron via preload
      script)
    </p>
    <button @click="record" :disabled="isPlaying">
      Record
    </button>
    <button @click="playback" :disabled="isRecording">
      Play
    </button>

    <button @click="toggleKeyboard" :class="{ active: useKeyboard }">
      Keyboard
    </button>

    <button @click="toggleMouse" :class="{ active: useMouse }">
      Mouse
    </button>

    <input type="text" v-model="loop" >
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      error: null,
      isRecording: false,
      isPlaying: false,
      useKeyboard: true,
      useMouse: true,
      loop: -1
    };
  },
  created() {},

  computed: {},

  methods: {
    async record() {
      if (!this.isRecording) {
        await axios.get("/api/record/start", {
          params: { input: this.getKeyboard() + this.getMouse() },
        });
      } 
      else {
        await axios.get("/api/record/stop");
      }
      this.isRecording = !this.isRecording;
    },
    async playback() {
     if (!this.isPlaying) {
        await axios.get("/api/playback/start", {
          params: { loop: this.loop },
        });
      } 
      else {
        await axios.get("/api/playback/stop");
      }
      this.isPlaying = !this.isPlaying;
    },
    toggleMouse() {
      this.useMouse = !this.useMouse;
    },

    toggleKeyboard() {
      this.useKeyboard = !this.useKeyboard;
    },

    getKeyboard: function() {
      if (this.useKeyboard) {
        return "keyboard,";
      }
      return "";
    },

    getMouse: function() {
      if (this.useMouse) {
        return "mouse,";
      }
      return "";
    },
  },
};
</script>

<style scoped>
.active {
  background-color: greenyellow;
}
</style>
