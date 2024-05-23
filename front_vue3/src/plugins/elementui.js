import { ElButton, ElSelect, ElScrollbar, ElTree, ElTable, ElForm,ElInputNumber, ElMessage} from 'element-plus'

export default (app) => {
    app.use(ElButton)
    app.use(ElSelect)
    app.use(ElScrollbar)
    app.use(ElTree)
    app.use(ElForm)
    app.use(ElTable)
    app.use(ElInputNumber)
    app.use(ElMessage)
}