// 시간타임 판단
export const getTime = () => {
    let message = ''
    let hours = new Date().getHours();
    if (hours <= 6) {
        message = 'toward morning'
    } else if (hours <= 12) {
        message = 'morning'
    } else if (hours <= 18) {
        message = 'afternoon'
    } else {
        message = 'evening'
    }
    return message
}