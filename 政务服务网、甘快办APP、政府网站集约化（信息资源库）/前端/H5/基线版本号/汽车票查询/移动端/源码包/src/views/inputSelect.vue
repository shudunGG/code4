<template>
  <div class="main">
    <input id="searchInput" placeholder="Type to search" :input="filterResults()" style="border: 1px solid black" ref="inputRef">
    <ul id="resultsList" style="display: none;"></ul>
  </div>
</template>

<script>
export default {
  name: 'Demo12',
  data() {
    return {
      list: [
        'Apple',
        'Banana',
        'Orange',
        'Grapes',
        'Cherry'
      ]
    }
  },
  methods: {
    filterResults() {
      // const query = document.getElementById('searchInput').value.toLowerCase()
      // const resultsList = document.getElementById('resultsList')
      // resultsList.innerHTML = ''
      //
      // // 过滤匹配项
      // const filteredResults = this.list.filter(item => item.toLowerCase().includes(query))
      //
      // // 将匹配项添加到下拉框中
      // filteredResults.forEach(result => {
      //   const li = document.createElement('li')
      //   li.textContent = result
      //   li.addEventListener('click', () => {
      //     document.getElementById('searchInput').value = result
      //     resultsList.style.display = 'none'
      //   })
      //   resultsList.appendChild(li)
      // })
      //
      // // 显示下拉框
      // resultsList.style.display = filteredResults.length > 0 ? 'block' : 'none'
    }
  }
}
</script>

<style scoped lang="less">
  .main {
    width: 100%;
    height: 60px;
  }
</style>
