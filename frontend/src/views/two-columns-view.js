import { unsafeCSS } from "lit";
import { html, LitElement, css } from "lit";

/**
 * `two-columns-view`
 *
 * TwoColumnsView element.
 *
 * @customElement
 * @polymer
 */
class TwoColumnsView extends LitElement {
  static get styles() {
    const includedStyles = {};
    includedStyles["common-styles"] =
      document.querySelector("dom-module[id='common-styles']")
        ?.firstElementChild?.content?.firstElementChild?.innerText ?? "";
    return [
      unsafeCSS(includedStyles["common-styles"]),
      css`
        :host {
          display: block;
        }

        .wrapper {
          margin: 0 auto; /* Horizontally center the layout grid if there is space around it */
        }

        #sidebar {
          grid-area: nav;
          background: var(--lumo-base-color)
            linear-gradient(
              hsla(125, 100%, 60%, 0.2),
              hsla(125, 100%, 60%, 0.2)
            );
        }

        #header {
          grid-area: header;
          background: var(--lumo-base-color)
            linear-gradient(
              hsla(349, 100%, 60%, 0.2),
              hsla(349, 100%, 60%, 0.2)
            );
        }

        #main {
          grid-area: content;
          background: var(--lumo-base-color)
            linear-gradient(
              hsla(227, 100%, 60%, 0.2),
              hsla(227, 100%, 60%, 0.2)
            );
        }

        #footer {
          grid-area: footer;
          background-color: lightcoral;
          background: var(--lumo-base-color)
            linear-gradient(
              hsla(318, 100%, 60%, 0.2),
              hsla(318, 100%, 60%, 0.2)
            );
        }

        @media only screen and (max-width: 699px) {
          .wrapper {
            display: grid;
            width: 90%;
            grid-template-columns: auto;
            grid-template-rows: auto;
            grid-template-areas:
              "nav"
              "header"
              "content"
              "footer";
          }
        }

        @media only screen and (min-width: 700px) and (max-width: 979px) {
          .wrapper {
            display: grid;
            width: 90%;
            grid-template-columns: 20% 5% auto;
            grid-template-rows: auto;
            grid-template-areas:
              "header header header"
              "nav . content"
              "footer footer footer";
          }
        }

        @media only screen and (min-width: 980px) {
          .wrapper {
            display: grid;
            grid-template-columns: 200px 40px auto;
            grid-template-rows: auto;
            grid-template-areas:
              "header header header"
              "nav . content"
              "footer footer footer";
            max-width: 1100px;
          }
        }
      `,
    ];
  }
  render() {
    return html`
      <article class="wrapper">
        <div id="sidebar"></div>
        <div id="header"></div>
        <div id="main"></div>
        <div id="footer"></div>
      </article>
      <vaadin-button
        style="margin-left: 40%;"
        onclick="window.location.href='https://github.com/vaadin/layout-examples/blob/master/src/main/java/com/vaadin/demo/views/TwoColumnsView.java'"
      >
        <img src="icons/Github.png" slot="prefix" />
        View source code
      </vaadin-button>
    `;
  }

  static get is() {
    return "two-columns-view";
  }
}

customElements.define(TwoColumnsView.is, TwoColumnsView);
