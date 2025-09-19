import requestJpaas from '../utils/request'
const { getData } = requestJpaas
export default {
    queryList(params) {
        return getData({
            columnId: 'd287511bbbbc4fbc93522dab49534358',
            // webId: '96f5e2abd4df4e9bb42c80c6bffeb755',
            params
        })
    }
}
