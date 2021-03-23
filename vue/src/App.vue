<template>
  <div id="app">
    <MacroPage />
  </div>
</template>

<script>
// import HomePage from './components/HomePage.vue'
import MacroPage from './components/MacroPage.vue'

export default {
  name: 'app',
  components: {
    MacroPage
  }
}
</script>|

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin: 60px;
}
</style>
