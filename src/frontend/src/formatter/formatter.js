let formatter = new Intl.NumberFormat();
export function format(type, value) {
	switch (type) {
        case 3: // percent
          return formatter.format(value) + "%";
        case 4: //currency
          return "$" + formatter.format(value);
        default: // count and rate
          return formatter.format(value);
      }
}