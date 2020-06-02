import Vue from 'vue';
import Notification from '@/components/Notification.vue';

const addNotification = function(notificationObject) {
  let NotificationComponent = Vue.extend(Notification)
  let componentInstance = new NotificationComponent({
    propsData: {type: notificationObject.type}
  })
  componentInstance.$slots.default = [notificationObject.text]
  componentInstance.$mount()
  return componentInstance
}

export default addNotification
