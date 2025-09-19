/**
 * 使用哈弗辛（Haversine）公式计算距离用户最近车站经纬度
 * @param {Object} userLocation - 用户位置对象，包含 latitude 和 longitude 属性
 * @param {Array} stations - 车站信息数组，包含 latitude 和 longitude 属性
 * @param {number} [radius=6371] - 地球半径，单位为千米，默认为 6371
 * @returns {Object} - 最近车站信息对象
 */


// 将角度转换为弧度的函数
function toRadians (angle) {
  return angle * (Math.PI / 180);
}

// 计算两个经纬度点之间距离的哈弗辛公式函数
function haversineDistance (lat1, lon1, lat2, lon2) {
  const R = 6371; // 地球半径，单位：公里
  const dLat = toRadians(lat2 - lat1); // 计算纬度差值的弧度
  const dLon = toRadians(lon2 - lon1); // 计算经度差值的弧度
  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2)) *
    Math.sin(dLon / 2) * Math.sin(dLon / 2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return R * c; // 返回两点间的距离（公里）
}

// 找到距离用户最近车站经纬度的函数
function findNearestStation (userLocation, stationInfo) {
  let stations = [];
  stationInfo.forEach((element) => {
    stations.push({ longitude: element.longitude, latitude: element.latitude });
  });


  let minDistance = Infinity; // 初始化最小距离为无穷大
  let nearestStation = null; // 初始化最近车站为 null

  for (let i = 0; i < stations.length; i++) {
    // 计算用户位置与当前车站的距离
    const distance = haversineDistance(
      parseFloat(userLocation.latitude),
      parseFloat(userLocation.longitude),
      parseFloat(stations[i].latitude),
      parseFloat(stations[i].longitude)
    );
    if (distance < minDistance) {
      minDistance = distance; // 更新最小距离
      nearestStation = stations[i]; // 更新最近车站
    }
  }
  let stationobject = {}

  stationInfo.forEach(i => {
    i.isShow = false
    if (i.longitude === nearestStation.longitude && i.latitude === nearestStation.latitude) {
      stationobject = i
      i.isShow = true
    }
  });
  return { stationobject, stationInfo }
}





export default findNearestStation;
