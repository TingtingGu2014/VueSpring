/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const userModule = {
  // This makes your getters, mutations, and actions accessed by, eg: 'myModule/myModularizedNumber' instead of mounting getters, mutations, and actions to the root namespace.
  namespaced: true,
  state: {
    userUUID: '',
    alias: '',
    email: '',
    detailsFetched: false,
    userInfoFetched: false,
    userInfo: null,
    details: null,
  },
  getters: {
    getUserAlias: state => state.alias,
    getUserEmail: state => state.email,
    isUserDetailsFetched: state => state.detailsFetched,
    isUserInfoFetched: state => state.userInfoFetched,
    getUserUUID: state => state.userUUID,
    getUserDetails: state => state.details,
    getUserInfo: state => state.userInfo,
  },
  mutations: {
    setUserUUID(state, newUserUUID) {
      state.userUUID = newUserUUID
    },
    setUserAlias(state, newUserAlias) {
      state.alias = newUserAlias
    },
    setDetailsFetched(state, newDetailsFetched) {
      state.detailsFetched = newDetailsFetched
    },
    setUserInfoFetched(state, newUserInfoFetched) {
      state.userInfoFetched = newUserInfoFetched
    },
    setUserEmail(state, newUserEmail) {
      state.email = newUserEmail
    },
    setUserDetails(state, newDetails) {
      state.details = newDetails
    },
    setUserInfo(state, newUserInfo) {
      state.userInfo = newUserInfo
    },
  }
}

export default userModule