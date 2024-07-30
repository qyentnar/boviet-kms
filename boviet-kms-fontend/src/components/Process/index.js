import MyProcessDesigner from "./process-designer";
import MyProcessPenal from "./refactor";

const components = [MyProcessDesigner, MyProcessPenal];

const install = function(Vue) {
  components.forEach(component => {
    Vue.component(component.name, component);
  });
};

// if (typeof window !== "undefined" && window.Vue) {
//   install(window.Vue);
// }

if (window.Vue) {
  window['hasRole'] = hasRole
  window['hasPermi'] = hasPermi
  Vue.use(install); // eslint-disable-line
}

export default install
