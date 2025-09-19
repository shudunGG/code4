import requestJpaas from "../utils/request";
const { getData } = requestJpaas;
export default {
  queryList(params) {
    return getData(
      params,
    );
  },
};
