let formatter = new Intl.NumberFormat();
export function format(type, value) {
	switch (type) {
        case "Percent":
          return formatter.format(value) + "%";
        case "Currency":
          return "$" + formatter.format(value);
        default:
          return formatter.format(value);
      }
}