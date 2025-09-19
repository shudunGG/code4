import { defineStore } from 'pinia'
import { ref } from 'vue'
export const useStore = defineStore(
  'store',
  () => {
    const count = ref(0)
    const banner = ref('')
    function increment () {
      count.value++
    }

    return { count, banner, increment }
  },
  {
    persist: {
      storage: sessionStorage
    }
  }
)
// export const useCounterStore = defineStore("counter", {
//   // 可以认为 state 是 store 的数据 (data)，getters 是 store 的计算属性 (computed)，而 actions 则是方法 (methods)。
//   state: () => ({ count: 0 }),
//   getters: {
//     double: (state) => state.count * 2,
//   },
//   actions: {
//     increment() {
//       this.count++;
//     },
//   },
// })
