import axios from 'axios';
import {Message} from 'element-ui'

//请求拦截
axios.interceptors.request.use(req => {
  return req;
}, err => {
  Message.error({message: '请求超时!'});
});

//响应拦截
axios.interceptors.response.use(resp => {
  //非get请求返回的RepMsg里
  // data.status为error直接打断，success继续传递 resp
  if (resp.status && resp.status == 200 && resp.data.status == 'error') {
    Message.error(resp.data.msg);
    return;
  }
  //get请求无msg,post put delete均返回RepMsg对象 有msg
  if (resp.data.msg) {
    Message.success(resp.data.msg);
  }
  return resp;
}, err => {
  if (err.response.status == 504 || err.response.status == 404) {
    Message.error('服务器访问失败');
  } else if (err.response.status == 401 || err.response.status == 403) {
    Message.error(err.response.data.msg);
  } else {
    if (err.response.data.msg) {
      Message.error(err.response.data.msg);
    }else{
      Message.error('未知错误!');
    }
  }
});


//get post put delete uploadpost
let base = '';

export const getRequest = (url,params) => {
  return axios({
    method: 'get',
    url: `${base}${url}`,
    params: params,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },

  });
};


export const postRequest = (url, params) => {
  return axios({
    method: 'POST',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function(data) {
      let ret = '';
      for (let it in data) {
        ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
      }
      return ret;
    }],
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
  });
};



export const putRequest = (url, params) => {
  return axios({
    method: 'put',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function(data) {
      let form = new FormData();
      for (let it in data) {
        form.append(it,data[it]);
      }
      return form;
    }],
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};

export const deleteRequest = (url, params) => {
  return axios({
    method: 'delete',
    url: `${base}${url}`,
    data: params,
    transformRequest: [function(data) {
      let form = new FormData();
      for (let it in data) {
        form.append(it,data[it]);
      }
      return form;
    }],
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};


export const uploadFileRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
};

