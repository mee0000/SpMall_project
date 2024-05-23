import{r as T,a as O,b as R}from"./index-dc245eb4.js";import{d as z,e as o,o as k,f as P,g as e,w as t,p as J,k as M,m as U,_ as Q,r as h,v as C,j as m,t as x,F as N,q as D,S as G,y as H,V as W,h as g,J as L,L as B}from"./index-06005ebc.js";const X=a=>(J("data-v-a925df92"),a=a(),M(),a),Y={class:"container"},Z=X(()=>U("span",{class:"text-gray-500"},"-",-1)),ee=z({__name:"index",props:["searchParams"],emits:["update:searchParams"],setup(a,{emit:y}){let v=y,p=a;const d=()=>{v("update:searchParams",p.searchParams)};return(S,u)=>{const l=o("el-input"),n=o("el-col"),c=o("el-form-item"),b=o("el-button"),_=o("el-form");return k(),P("div",Y,[e(_,{inline:!0,model:a.searchParams,class:"demo-form-inline","label-width":"80px"},{default:t(()=>[e(c,{label:"price by"},{default:t(()=>[e(n,{span:11},{default:t(()=>[e(l,{modelValue:a.searchParams.min,"onUpdate:modelValue":u[0]||(u[0]=w=>a.searchParams.min=w),type:"number",placeholder:"0",style:{width:"100%"}},null,8,["modelValue"])]),_:1}),e(n,{span:2,class:"text-center"},{default:t(()=>[Z]),_:1}),e(n,{span:11},{default:t(()=>[e(l,{modelValue:a.searchParams.max,"onUpdate:modelValue":u[1]||(u[1]=w=>a.searchParams.max=w),type:"number",placeholder:"100",style:{width:"100%"}},null,8,["modelValue"])]),_:1})]),_:1}),e(c,null,{default:t(()=>[e(l,{modelValue:a.searchParams.keyword,"onUpdate:modelValue":u[2]||(u[2]=w=>a.searchParams.keyword=w),placeholder:"Please input"},{append:t(()=>[e(b,{icon:"Search",onClick:d})]),_:1},8,["modelValue"])]),_:1})]),_:1},8,["model"])])}}});const te=Q(ee,[["__scopeId","data-v-a925df92"]]),ae=U("h4",null,"상품 SKU 상세페이지",-1),le=z({__name:"Drawer",props:["drawer"],emits:["update"],setup(a,{emit:y}){const v=h("rtl");let p=y;const d=()=>{p("update")};return(S,u)=>{const l=o("el-col"),n=o("el-row"),c=o("el-tag"),b=o("el-drawer");return k(),C(b,{"model-value":a.drawer.isShow,direction:v.value,"before-close":d},{header:t(()=>[ae]),default:t(()=>[e(n,null,{default:t(()=>[e(l,{span:6},{default:t(()=>[m("skuName")]),_:1}),e(l,{span:18},{default:t(()=>[m(x(a.drawer.skuName),1)]),_:1})]),_:1}),e(n,null,{default:t(()=>[e(l,{span:6},{default:t(()=>[m("설명")]),_:1}),e(l,{span:18},{default:t(()=>[m(x(a.drawer.decoration),1)]),_:1})]),_:1}),e(n,null,{default:t(()=>[e(l,{span:6},{default:t(()=>[m("가격")]),_:1}),e(l,{span:18},{default:t(()=>[m(x(a.drawer.price),1)]),_:1})]),_:1}),e(n,null,{default:t(()=>[e(l,{span:6},{default:t(()=>[m("상품속성")]),_:1}),e(l,{span:18},{default:t(()=>[(k(!0),P(N,null,D(a.drawer.skuAttrValueList,_=>(k(),C(c,{key:_},{default:t(()=>[m(x(_.valueName),1)]),_:2},1024))),128))]),_:1})]),_:1}),e(n,null,{default:t(()=>[e(l,{span:6},{default:t(()=>[m("세일 속성")]),_:1}),e(l,{span:18},{default:t(()=>[(k(!0),P(N,null,D(a.drawer.skuSaleAttrValueList,_=>(k(),C(c,{key:_.id,style:{"margin-right":"5px"}},{default:t(()=>[m(x(_.saleAttrValueName),1)]),_:2},1024))),128))]),_:1})]),_:1})]),_:1},8,["model-value","direction"])}}}),ne=["src"],re=z({__name:"index",setup(a){let y=h([]),v=h(0),p=h(1),d=h(10),S=h(3),u=G(),l=h({id:"",spuId:"",price:"",quantity:"",skuName:"",category2Id:"",skuDefaultImg:"",discount:"",decoration:"",skuAttrValueList:[],skuSaleAttrValueList:[]}),n=h({keyword:"",min:"",max:"",category2Id:""});H(()=>u.category2Id,()=>{if(u.category2Id==""){y.value=[];return}n.value={keyword:"",min:"",max:"",category2Id:""},c()});const c=async()=>{n.value.category2Id=u.category2Id;const r=await T(p.value,d.value,n.value);r.code==200&&(y.value=r.data.rows,S.value=r.data.total)},b=r=>{Object.assign(n.value,r),console.log(n.value,"---------"),c()},_=r=>{l.value={...r,isShow:!0}},w=async r=>{const f=r.status==1?2:1,V={...r,status:f};(await O(V)).code==200&&c()},q=async r=>{(await R(r.id)).code==200?(B({type:"success",message:"SKU 삭제 완료"}),c()):B({type:"error",message:"SKU 삭제 실패"})},A=()=>{l.value.isShow=!1};return W(()=>{u.$reset()}),(r,f)=>{const V=o("Category"),i=o("el-table-column"),I=o("el-button"),j=o("el-popconfirm"),E=o("el-table"),F=o("el-pagination"),K=o("el-card");return k(),P(N,null,[e(V,{scene:g(v)},null,8,["scene"]),e(K,{style:{"margin-top":"10px"}},{default:t(()=>[e(te,{searchParams:g(n),"onUpdate:searchParams":b},null,8,["searchParams"]),e(E,{data:g(y),style:{width:"100%"},border:""},{default:t(()=>[e(i,{label:"No",type:"index",align:"center",width:"80px"}),e(i,{label:"img",width:"120"},{default:t(({row:s})=>[U("img",{src:s.skuDefaultImg,style:{width:"100px",height:"100px"}},null,8,ne)]),_:1}),e(i,{prop:"createTime",label:"등록일",width:"120",type:"date"}),e(i,{prop:"skuName",label:"Name",width:"120"}),e(i,{prop:"price",label:"Pirce",width:"120"}),e(i,{prop:"discount",label:"Discount",width:"120"}),e(i,{prop:"decoration",label:"Decoration","show-overflow-tooltip":"",width:"120px"}),e(i,{prop:"quantity",label:"Qty",width:"120"}),e(i,{fixed:"right",label:"Operations",width:"250"},{default:t(({row:s})=>[e(I,{type:s.status==1?"primary":"info",size:"small",icon:s.status==1?"Top":"Bottom",onClick:$=>w(s)},null,8,["type","icon","onClick"]),e(I,{type:"warning",size:"small",icon:"InfoFilled",onClick:$=>_(s)},null,8,["onClick"]),e(I,{type:"success",size:"small",icon:"Edit"}),e(j,{title:`${s.skuName}을/를 삭제하겠습니까?`,onConfirm:$=>q(s)},{reference:t(()=>[e(I,{type:"danger",plain:"",size:"small",icon:"Delete"})]),_:2},1032,["title","onConfirm"])]),_:1})]),_:1},8,["data"]),e(F,{"current-page":g(p),"onUpdate:currentPage":f[0]||(f[0]=s=>L(p)?p.value=s:p=s),"page-size":g(d),"onUpdate:pageSize":f[1]||(f[1]=s=>L(d)?d.value=s:d=s),layout:" prev, pager, next,jumper,->,sizes, total",total:g(S),"page-sizes":[3,5,7,10],onSizeChange:f[2]||(f[2]=s=>c(g(d))),onCurrentChange:c},null,8,["current-page","page-size","total"])]),_:1}),e(le,{drawer:g(l),onUpdate:A},null,8,["drawer"])],64)}}});export{re as default};
