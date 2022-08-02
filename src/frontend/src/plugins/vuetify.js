import Vue from 'vue'
import Vuetify from 'vuetify/lib/framework'

Vue.use(Vuetify)

export default new Vuetify({
	theme: {
		options: { customProperties: true },
		themes: {
			light: {
				primary: '#3b5a98',
				secondary: '#3aa38f',
				accent: '#f6921e'
			}
		}
	}
})
