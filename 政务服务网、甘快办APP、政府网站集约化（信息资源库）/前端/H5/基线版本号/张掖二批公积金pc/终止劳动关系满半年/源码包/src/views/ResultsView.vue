<template>
  <div>
    <section class="query-container">
      <div class="pagetitle">上传资料</div>
      <!-- :model="formData"
        ref="formData"
        :rules="formRules" -->
      <el-form
        style="padding: 0.2rem 0.4rem"
        label-position="top"
        label-width="100px"
      >
        <div v-for="(item, index) in list" :key="index">
          <el-form-item>
            <template slot="label" >
               <span style="color: red;" >*</span> {{ item.lbmc }}
            </template>
            <el-upload
              class="avatar-uploader"
              ref="upload"
              :action="action"
              list-type="picture-card"
              accept="image/jpg,image/png,image/jpeg"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove(item)"
              :http-request="uploadFile(item, index)"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <div class="el-upload__tip">
              提示：文件格式为图片，支持jpg,png格式，支持上传多个，文件大小不能超过6M
            </div>
          </el-form-item>
        </div>

        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="" />
        </el-dialog>
        <div class="form-btn">
          <input
            type="button"
            class="submit"
            value="提交"
            @click="queryList('formData')"
          />
          <input type="button" value="返回" @click="resetForm" />
        </div>
      </el-form>
    </section>
  </div>
</template>
<script>
import IndexApi from "@/api/index";
import tools from "../tools/tool";
// import sm4 from "@/utils/sm4";
export default {
  name: "ResultsView",
  mounted() {
    this.getFileType(); // 获取收件目录接口
  },
  activated() {
    this.getFileType(); // 获取收件目录接口
  },
  data() {
    return {
      userinfo: {},
      action: "fileUuid",
      show: true,
      dialogImageUrl: "",
      dialogVisible: false,
      userInfo: {}, //用户信息
      list: [],
      fileList: [],
      fileListArr: {},
      fileList1: [],
      fileList2: [],
      fileList3: [],
      fileList4: [],
      fileList5: [],
      fileList6: [],
      sbcode: "",
      loading: false,
    };
  },
  created() {
    this.userInfo = this.$store.state.userInfo;
    this.authCode = this.$store.state.authCode;
    this.token = this.$store.state.token;
    this.jlbh = this.$route.query.jlbh;
    console.log(this.userInfo, "this.userInfo");
  },
  methods: {
    beforeAvatarUpload(file) {
      console.log(file, "file");
      const isJPG = file.type;
      const isLt6M = file.size / 1024 / 1024 < 6;
      console.log(isJPG !== "image/jpeg" && isJPG !== "image/png");
      if (isJPG !== "image/jpeg" && isJPG !== "image/png") {
        // this.$message.error("上传的文件只能是 JPG/PNG 格式!");
        return;
      }
      if (!isLt6M) {
        // this.$message.error("上传的文件大小不能超过 6MB!");
        return;
      }
      const result = isJPG && isLt6M;
      return result;
    },
    // 获取档案附件接口
    getFileType() {
      const loading = this.$loading({
        fullscreen: true,
        // lock: true,
        text: "正在加载业务信息,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            // jlbh: "1735601572700573698",
            jlbh: this.jlbh,
            xxly: "G",
          })
        ),
        ds: "lx",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "dzdaList.action",
      };
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res);
          if (res.code == 200 && res.success) {
            res = JSON.parse(tools.decryptSM2(res.data.data));
            this.list = res.result.data;
            console.log(this.list, "this.list");
            this.list.forEach((item, index) => {
              if (item.lbmc == "一、基本资料" || item.lbmc == "二、提取资料") {
                this.list.splice(index, 1);
              }
            });
            this.list.forEach((item) => {
              if (item.dzdaxxVoList.length > 0) {
                console.log(item, "材料大于一");
                this.handleRemove1(item);
              }
            });
            loading.close();
            console.log(this.list, "获取收件目录");
          } else {
            this.$message.info(res.root.head.retmsg);
            loading.close();
          }
        })
        .catch(() => {
          loading.close();
          this.$message.error("网络异常，请稍后重试！");
        });
    },
    // 获取档案附件接口
    getFileType1() {
      const loading = this.$loading({
        fullscreen: true,
        // lock: true,
        text: "正在加载业务信息,请稍等",
        spinner: "el-icon-loading",
        background: "rgba(f,f,f,0.7)",
      });
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            // jlbh: "1735601572700573698",
            jlbh: this.jlbh,
            xxly: "G",
          })
        ),
        ds: "lx",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "dzdaList.action",
      };
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res);
          if (res.code == 200 && res.success) {
            res = JSON.parse(tools.decryptSM2(res.data.data));
            this.list = res.result.data;
            console.log(this.list, "this.list");
            this.list.forEach((item, index) => {
              if (item.lbmc == "一、基本资料" || item.lbmc == "二、提取资料") {
                this.list.splice(index, 1);
              }
            });
            loading.close();
            console.log(this.list, "获取收件目录");
          } else {
            this.$message.info(res.root.head.retmsg);
            loading.close();
          }
        })
        .catch(() => {
          loading.close();
          this.$message.error("网络异常，请稍后重试！");
        });
    },
    uploadFile(item, key) {
      return (file) => {
        if (this.beforeAvatarUpload(file.file)) {
          const loading = this.$loading({
            fullscreen: true,
            lock: true,
            text: "上传中，请稍后",
            spinner: "el-icon-loading",
            background: "rgba(f,f,f,0.7)",
          });
          let files = file.file;
          let param = {
            tokenNo: this.token,
            authCode: this.authCode,
          };
          // for(){}
          IndexApi.queryList("wjsc", this.action, param, files)
            .then((res) => {
              if (res.code == 200 && res.success) {
                // Toast.clear()
                console.log(res.data, "上传文件");
                let param = {
                  params: tools.encryptSM2(
                    JSON.stringify({
                      jlbh: this.jlbh,
                      // jlbh: "1735601572700573698",
                      xxly: "G",
                      basestr: res.data,
                      lbbh: item.lbbh,
                      pc: "1",
                    })
                  ),
                  ds: "zy",
                  token: this.$store.state.token,
                  authCode: this.$store.state.authCode,
                  method: "dzdaUpload.action",
                };
                console.log(param, "param");
                IndexApi.queryList("tygjj", "commonGjj", param)
                  .then((res) => {
                    console.log(res, "上传接口");
                    res = JSON.parse(tools.decryptSM2(res.data.data));
                    console.log(res, "解密后的上传");
                    this.sbcode = res.result.code;
                    if (res.result.code == "00" && res.result.data) {
                      loading.close();
                      this.getFileType1();
                      this.$message.success({
                        message: "上传成功！",
                      });
                    } else {
                      loading.close();
                      this.$refs.upload[key].uploadFiles.splice(
                        this.$refs.upload[key].uploadFiles.length - 1,
                        1
                      );
                      this.$forceUpdate();
                      this.$message.error({
                        message: "上传失败，请重试！",
                      });
                    }
                  })
                  .catch(() => {
                    loading.close();
                    this.$refs.upload[key].uploadFiles.splice(
                      this.$refs.upload[key].uploadFiles.length - 1,
                      1
                    );
                    this.$forceUpdate();
                    this.$message.error({
                      message: "网络异常，请稍后重试！",
                    });
                  });
              } else {
                // Toast.clear()
                loading.close();
                this.$refs.upload[key].uploadFiles.splice(
                  this.$refs.upload[key].uploadFiles.length - 1,
                  1
                );
                this.$forceUpdate();
                this.$message.error("网络异常，请稍后重试");
              }
              // console.log(index);
            })
            .catch(() => {
              loading.close();
              this.$refs.upload[key].uploadFiles.splice(
                this.$refs.upload[key].uploadFiles.length - 1,
                1
              );
              this.$forceUpdate();
              this.$message.error("网络异常，请稍后重试");
            });
        } else {
          this.$refs.upload[key].uploadFiles.splice(
            this.$refs.upload[key].uploadFiles.length - 1,
            1
          );
          this.$forceUpdate();
          this.$message.error("上传的文件大小不能超过 6MB!");
        }
      };
    },
    handleRemove(item) {
      return () => {
        console.log(item, "item");
        const loading = this.$loading({
          fullscreen: true,
          lock: true,
          text: "删除中，请稍后",
          spinner: "el-icon-loading",
          background: "rgba(f,f,f,0.7)",
        });
        let param = {
          params: tools.encryptSM2(
            JSON.stringify({
              // jlbh: "1735601572700573698",
              jlbh: this.jlbh,
              xxly: "G",
              dabh: item.dzdaxxVoList[item.dzdaxxVoList.length - 1].dabh,
              lbbh: item.dzdaxxVoList[item.dzdaxxVoList.length - 1].lbbh,
            })
          ),
          ds: "lx",
          token: this.$store.state.token,
          authCode: this.$store.state.authCode,
          method: "dzdaDelete.action",
        };
        IndexApi.queryList("tygjj", "commonGjj", param)
          .then((res) => {
            console.log(res, "删除接口");
            res = JSON.parse(tools.decryptSM2(res.data.data));
            console.log(res, "解密后的删除");
            if (res.result.code == "00" && res.result.msg == "成功") {
              loading.close();
              this.getFileType1();
              this.$message.success({
                message: "删除成功！",
              });
            } else {
              loading.close();
              this.$message.error({
                message: "删除失败，请重试！",
              });
            }
          })
          .catch(() => {
            loading.close();
            this.$message.error({
              message: "网络异常，请稍后重试！",
            });
          });
      };
    },
    handleRemove1(item) {
      console.log(item, "item");
      let param = {
        params: tools.encryptSM2(
          JSON.stringify({
            // jlbh: "1735601572700573698",
            jlbh: this.jlbh,
            xxly: "G",
            dabh: item.dzdaxxVoList[item.dzdaxxVoList.length - 1].dabh,
            lbbh: item.dzdaxxVoList[item.dzdaxxVoList.length - 1].lbbh,
          })
        ),
        ds: "lx",
        token: this.$store.state.token,
        authCode: this.$store.state.authCode,
        method: "dzdaDelete.action",
      };
      IndexApi.queryList("tygjj", "commonGjj", param)
        .then((res) => {
          console.log(res, "删除接口");
          res = JSON.parse(tools.decryptSM2(res.data.data));
          console.log(res, "解密后的删除");
          if (res.result.code == "00" && res.result.msg == "成功") {
            console.log("ceshi");
          } else {
            console.log("ceshi");
          }
        })
        .catch(() => {
          console.log("ceshi");
        });
    },
    queryList() {
      let result = this.list.every((item) => {
        if (item.dzdaxxVoList.length == 0) {
          return false;
        } else {
          return true;
        }
      });
      if (!result) {
        this.$message({
          type: "info",
          message: `请上传附件`,
        });
      } else {
        let param = {
          params: tools.encryptSM2(
            JSON.stringify({
              // jlbh: "1735601572700573698",
              jlbh: this.jlbh,
              xxly: "G",
            }),
            this.publicKey1
          ),
          ds: "lx",
          token: this.$store.state.token,
          authCode: this.$store.state.authCode,
          method: "dzdaUpOver.action",
        };
        IndexApi.queryList("tygjj", "commonGjj", param).then((res) => {
          res = JSON.parse(tools.decryptSM2(res.data.data, this.privateKey1));
          if (res.result.code == "00" && res.result.msg == "提交成功") {
            this.$router.push({ path: "/no-results" });
          } else {
            this.$message.error({
              message: res.result.msg || "提交失败，请稍后重试！",
            });
          }
        });
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },

    resetForm() {
      this.$router.push("/extractInfo");
    },
  },
};
</script>

<style scoped>
.yzm {
  width: 320px;
}
@media screen and (max-width: 800px) {
  .yzm {
    width: 130px;
  }
}
.pagetitle {
  font-weight: 600;
  padding-left: 10px;
  margin-left: 5px;
  border-left: 3px solid rgb(24, 144, 255);
}

.flex {
  display: flex;
  justify-content: space-between;
}
.el-date-editor.el-input {
  width: 100%;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.el-upload__tip {
  color: #aaa;
}
.el-upload__span {
  /* font-size: 20px; */
  width: 30px;
  position: absolute;
  bottom: -20px;
  left: 40%;
}
</style>
