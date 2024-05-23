import BaseButton from './ui/BaseButton.vue'
import PageNation from './pagenation/index.vue'
import BaseBadge from './ui/BaseBadge.vue'
import BaseDialog from './ui/BaseDialog.vue'
import BaseContainer from './ui/BaseContainer.vue'

const allGlobalComponent = { BaseButton, PageNation, BaseBadge, BaseDialog, BaseContainer } 

export default {
    install(app) {
        Object.keys(allGlobalComponent).forEach(key => {
            app.component(key, allGlobalComponent[key])
        })
    }
}