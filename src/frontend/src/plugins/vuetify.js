import Vue from 'vue'
import Vuetify from 'vuetify/lib/framework'

Vue.use(Vuetify)

export default new Vuetify({
	theme: {
		options: { customProperties: true },
		themes: {
			light: {
				primary: '#b8237e',
				secondary: '#3b5a98',
				accent: '#3aa38f',
				yellow: '#f6921e'
			}
		}
	}
})
