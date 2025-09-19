<template>
  <div class="page" v-if="Object.keys(cfcxmzzxxList).length>0">
    <div class="page_w">
      <div class="container-wrap">
        <div class="title">{{ info.checkItem || '/'}}</div>
        <div class="checklist">
          报告时间：{{ cfcxmzzxxList[0].date || '/'}}
        </div>
        <div class="checklist">
          <div class="checklist_l">
            申请医生：{{ cfcxmzzxxList[0].applyName || '/'}}
          </div>
          <div class="checklist_r">
            检验医生：{{ cfcxmzzxxList[0].checkName || '/'}}
          </div>
        </div>
        <div class="checklist">
          <div class="checklist_l">
            报告类型：{{ info.reportType==0? "检验报告" : "检验报告" || '/' }}
          </div>
          <div class="checklist_r">
            报告医生：{{ cfcxmzzxxList[0].reportName || '/'}}
          </div>
        </div>
      </div>
      <div class="container-wrap padd">
        <div class="table-responsive">
          <table class="table">
            <thead>
              <tr>
                <th>检查项目</th>
                <th>检查结果</th>
                <th>参考值</th>
                <th>单位</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in tableData" :key="index">
                <td>{{ item.checkItem || '-'}}</td>
                <td>{{ item.result || '-'}}</td>
                <td>{{ item.range || '-'}}</td>
                <td>{{ item.unit || '-'}}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <!-- 底部 -->
    <div class="footer">
      <div class="footer-content">本服务由高台县提供</div>
    </div>
  </div>
</template>
<script>
import { serveAPI } from '../api/serve'
import { Toast } from 'vant'
import tools from '../tools/tool'
export default {
  data () {
    return {
      info: {},
      cfcxmzzxxList: {},
      tableData: []
    }
  },
  created () {
    console.log(this.$route.query.info, '11')
    this.info = JSON.parse(this.$route.query.info)
    this.getcfcxmzmx(this.info.id)
  },
  methods: {
    // 1.2.3.获取检验报告单号获取详细信息
    getcfcxmzmx (val) {
      Toast.loading({
        duration: 0, // 持续展示 toast
        message: '加载中...',
        forbidClick: true
      })
      let params = {
        results: tools.encryptSM2(JSON.stringify({
          authCode: '',
          token:
            this.$store.state.userInfo.tokenNo, //token移动端和pc端传值
          id: val,
          dataType:'gtzyy'
        }))
      }
      serveAPI
        .queryList('zyyyjkrzmpgab', 'zyyycheckInfo', params)
        .then(res => {
          console.log(res, '1.2.3.获取检验报告单号获取详细信息')
          if (res.code == '200' && res.success) {
            console.log('报告详情查询')
            Toast.clear()
            let result = JSON.parse(tools.decryptSM2(res.data))
            console.log(result, '高台县医院报告详情查询')
            this.cfcxmzzxxList = result
            this.tableData = result
          } else {
            Toast.clear()
            Toast({
              message: res.message || '未获取到数据'
            })
          }
        })
        .catch(() => {
          Toast.clear()
          Toast({
            message: '网络异常，请稍后重试！'
          })
        })
    }
  }
}
</script>

<style scoped lang="less">
.page {
  width: 100%;
  box-sizing: border-box;
  background: #f8fbff;
  color: #333;
  padding: 12px 12px 0;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 100vh;
  .container-wrap {
    // height: 587px;
    border-radius: 4px;
    background: linear-gradient(180deg, #fff 0%, #fff 100%);
    box-shadow: 0 0 12px #237dff26;
    padding: 12px;
    margin-bottom: 12px;
    .title {
      font-weight: 700;
      font-size: 15px;
      padding: 0 0 8px;
      color: #3f87f3;
    }
    .checklist {
      display: flex;
      justify-content: space-between;
      font-weight: 700;
      font-size: 12px;
      line-height: 24px;
      text-align: left;
      color: #333;

      .checklist_r {
        margin-right: 79px;
      }
    }
    .field {
      display: flex;
      justify-content: space-between;
      border-bottom: 1px solid #eeeeee;
      padding: 12px 0;

      .field_text {
        font-size: 13px;
      }
      .field_value {
        color: #999;
      }
    }
  }
  // 底部
  .footer {
    width: 100%;
    height: 127.5px;
    background-image: url(../assets/images/bgc.png);
    background-repeat: no-repeat;
    background-size: contain;
    background-position: bottom;
    position: relative;
    bottom: 0;
    left: 0;
    z-index: 0;

    .footer-content {
      width: 100%;
      text-align: center;
      font-size: 14px;
      color: #999;
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
.money {
  color: #3f87f3 !important;
}
.padd {
  padding: 0 !important;
}
.pad {
  padding: 12px !important;
}
/* 在这里添加自定义样式以移动端自适应 */
.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 8px;
  text-align: center;
}

.table th {
  background-color: #f2f2f2;
}
.table th:nth-child(1) {
  width: 30%;
}
.table th:nth-child(2) {
  width: 25%;
}
.table th:nth-child(3) {
  width: 20%;
}
.table th:nth-child(4) {
  width: 25%;
}

.table-responsive {
  overflow-x: auto;
}
</style>
