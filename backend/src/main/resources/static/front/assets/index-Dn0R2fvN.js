import{v as p,d as _,s as u,x as h,e as m,o as a,c as o,f as s,F as f,i as g,j as v,g as x,h as b,t as i,p as L,q as k,_ as q}from"./index-HA-C2-6D.js";const r=p.create({baseURL:"/mock",timeout:5e3});r.interceptors.request.use(e=>e);r.interceptors.response.use(e=>e.data,e=>Promise.reject(new Error("faile")));const w=()=>r.get("/banner"),y=e=>(L("data-v-0a4e1ced"),e=e(),k(),e),B={class:"products-catagories-area clearfix"},I={class:"amado-pro-catagory clearfix"},S={class:"single-products-catagory clearfix"},j=["src"],C={class:"hover-content"},N=y(()=>s("div",{class:"line"},null,-1)),E=_({__name:"index",setup(e){u(()=>{d()});let n=h([]);const d=async()=>{const{data:c}=await w();Object.assign(n,c)};return(c,F)=>{const l=m("base-button");return a(),o("div",B,[s("div",I,[(a(!0),o(f,null,g(v(n),t=>(a(),o("div",S,[x(l,{link:"",to:{path:"/shop"}},{default:b(()=>[s("img",{src:t.imgUrl,alt:""},null,8,j),s("div",C,[N,s("p",null,i(t.price),1),s("h4",null,i(t.productName),1)])]),_:2},1024)]))),256))])])}}}),V=q(E,[["__scopeId","data-v-0a4e1ced"]]);export{V as default};
