<template>
  <div>
    <h1>Automate Me</h1>
    <p>
      Press F9 to Start/Stop Recording<br />
      Press F10 to Start/Stop Playback<br />
    </p>
    <button @click="record" :disabled="isPlaying">
      {{ recordText }}
    </button>
    <button @click="playback" :disabled="isRecording">
      {{ playText }}
    </button>

    <button @click="toggleKeyboard" :class="{ active: useKeyboard }">
      Keyboard
    </button>

    <button @click="toggleMouse" :class="{ active: useMouse }">
      Mouse
    </button>

    <div>Repeat: <input type="text" v-model="loop" /></div>
    <span>
      Repeat -1 is infinite
    </span>
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
      loop: -1,
      recordText: "Record",
      playText: "Play",
    };
  },
  created() {
    // TODO: Register with SockJS/STOMP here next time
  },

  computed: {},

  methods: {
    async record() {
      if (!this.isRecording) {
        this.recordText = "Stop";
        const response = await axios.get("/api/record/start", {
          params: { input: this.getKeyboard() + this.getMouse() },
        });

        console.log(response.data);
        this.recordText = "Record (again)";
      } else {
        await axios.get("/api/record/stop");
        this.recordText = "Record";
      }
      this.isRecording = !this.isRecording;
    },
    async playback() {
      if (!this.isPlaying) {
        await axios.get("/api/playback/start", {
          params: { loop: this.loop },
        });
        this.playText = "Stop";
      } else {
        await axios.get("/api/playback/stop");
        this.playText = "Play";
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

span {
  font-size: 12px;
  color: gray;
}
</style>
